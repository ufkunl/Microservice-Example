package com.microservice.customer.service;

import com.microservice.customer.dto.CustomerRegistrationRequest;
import com.microservice.customer.dto.FraudCheckResponse;
import com.microservice.customer.client.FraudClient;
import com.microservice.customer.dto.KafkaNotification;
import com.microservice.customer.entity.Customer;
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

        Customer customer = Customer.builder()
                .firstName(customerRegistrationRequest.getFirstName())
                .lastName(customerRegistrationRequest.getLastName())
                .email(customerRegistrationRequest.getEmail())
                .build();

        customerRepository.saveAndFlush(customer);

        log.info("new customer registration {}", customerRegistrationRequest);
        //todo: check if email valid
        //todo: check if email not taken

        FraudCheckResponse response = fraudClient.isFraudulentCustomer(customer.getId());
        if (response != null && response.getIsFraudster()){
            throw new IllegalStateException("fraudster");
        }
        sendNotification(customer.getEmail());
    }


    private void sendNotification(String mail){
        try{
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
        }catch (Exception e){
            log.error("Kafka send error: {}",e.getMessage());
        }

    }
}
