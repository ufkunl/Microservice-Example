package com.microservice.customer.service;

import com.microservice.customer.client.fraud.FraudClient;
import com.microservice.customer.client.fraud.domain.FraudCheckClientDetailResponse;
import com.microservice.customer.client.fraud.domain.FraudCheckClientResponse;
import com.microservice.customer.dto.*;
import com.microservice.customer.entity.Customer;
import com.microservice.customer.exception.CustomerNotFoundException;
import com.microservice.customer.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

@Slf4j
@Service
public class CustomerService {

    @Value("${notification.kafka.topic}")
    private String kafkaTopic;

    private final KafkaTemplate<String, KafkaNotification> kafkaTemplate;
    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;


    @Autowired
    public CustomerService(KafkaTemplate<String, KafkaNotification> kafkaTemplate, CustomerRepository customerRepository, FraudClient fraudClient) {
        this.kafkaTemplate = kafkaTemplate;
        this.customerRepository = customerRepository;
        this.fraudClient = fraudClient;
    }

    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {


        FraudCheckClientDetailResponse response = fraudClient.isFraudster(customerRegistrationRequest.getEmail());

//        if (response.getIsFraudster()) {
//            throw new IllegalStateException("fraudster");
//        }

        Customer customer = Customer.builder()
                .firstName(customerRegistrationRequest.getFirstName())
                .lastName(customerRegistrationRequest.getLastName())
                .email(customerRegistrationRequest.getEmail())
                .removed(false)
                .build();

        customerRepository.saveAndFlush(customer);

//        FraudChangeRequest request = new FraudChangeRequest();
//        request.setEmail(customer.getEmail());
//        request.setIsFraud(false);
//        fraudClient.changeFraudStatus(request);

        log.info("new customer registration {}", customerRegistrationRequest);
        //todo: check if email valid
        //todo: check if email not taken


        sendNotification(customer.getEmail());
    }

    public void complainCustomer(ComplainCustomerRequest request) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found."));
        customer.setRemoved(true);
        customerRepository.save(customer);

//        FraudChangeRequest fraudChangeRequest = new FraudChangeRequest();
//        fraudChangeRequest.setIsFraud(true);
//        fraudChangeRequest.setEmail(customer.getEmail());
//        fraudClient.changeFraudStatus(fraudChangeRequest);
    }

    private void sendNotification(String mail) {
        try {
            //KafkaMessage nesnesini queue ya kafkaTemplate ile gönderiyor.
            KafkaNotification kafkaNotification = new KafkaNotification();
            kafkaNotification.setContent("Üyeliğiniz başarılı bir şekilde yapıldı.");
            kafkaNotification.setTitle("Hoşgeldiniz.");
            kafkaNotification.setEmail(mail);
            UUID messageUUID = UUID.randomUUID(); //Kuyrukta ilgili mesaja key setledim.
            ListenableFuture<SendResult<String, KafkaNotification>> future = kafkaTemplate.send(kafkaTopic, messageUUID.toString(), kafkaNotification);
            future.addCallback(new ListenableFutureCallback<SendResult<String, KafkaNotification>>() {
                @Override
                public void onSuccess(SendResult<String, KafkaNotification> result) {
                    log.info("Sent message=[{}] with offset=[{}}]", kafkaNotification.getContent(), result.getRecordMetadata().offset());
                }

                @Override
                public void onFailure(Throwable ex) {
                    log.error("Unable to send message=[{}] due to : {}", kafkaNotification.getContent(), ex.getMessage());
                }
            });
        } catch (Exception e) {
            log.error("Kafka send error: {}", e.getMessage());
        }

    }
}
