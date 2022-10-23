package com.microservice.customer.service;

import com.microservice.coreservice.enums.RestResponseCode;
import com.microservice.customer.client.fraud.FraudClient;
import com.microservice.customer.dto.*;
import com.microservice.customer.entity.Customer;
import com.microservice.customer.exception.CustomerException;
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

        FraudCheckResponse response = fraudClient.isFraudster(customerRegistrationRequest.getEmail());
        if (response.isFraudster()) {
            throw new CustomerException(RestResponseCode.FRAUD_CUSTOMER_ERROR);
        }
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRequest.getFirstName())
                .lastName(customerRegistrationRequest.getLastName())
                .email(customerRegistrationRequest.getEmail())
                .removed(false)
                .build();

        customerRepository.saveAndFlush(customer);

        FraudChangeRequest request = new FraudChangeRequest();
        request.setEmail(customer.getEmail());
        request.setIsFraud(false);
        fraudClient.changeFraudStatus(request);
        log.info("new customer registration {}", customerRegistrationRequest);

        NotificationRequest notification = new NotificationRequest();
        notification.setContent("Üyeliğiniz başarılı bir şekilde yapıldı.");
        notification.setTitle("Hoşgeldiniz.");
        notification.setMail(customer.getEmail());
        sendNotification(notification);
    }

    public void complainCustomer(ComplainCustomerRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomerException(RestResponseCode.CUSTOMER_NOT_FOUND));
        customer.setRemoved(true);
        customerRepository.save(customer);

        FraudChangeRequest fraudChangeRequest = new FraudChangeRequest();
        fraudChangeRequest.setIsFraud(true);
        fraudChangeRequest.setEmail(customer.getEmail());
        fraudClient.changeFraudStatus(fraudChangeRequest);
    }

    private void sendNotification(NotificationRequest notification) {
        try {
            //KafkaMessage nesnesini queue ya kafkaTemplate ile gönderiyor.
            KafkaNotification kafkaNotification = new KafkaNotification();
            kafkaNotification.setContent(notification.getContent());
            kafkaNotification.setTitle(notification.getTitle());
            kafkaNotification.setEmail(notification.getMail());
            UUID messageUUID = UUID.randomUUID(); //Kuyrukta ilgili mesaja key setledim.
            ListenableFuture<SendResult<String, KafkaNotification>> future = kafkaTemplate.send(kafkaTopic, messageUUID.toString(), kafkaNotification);
            future.addCallback(new ListenableFutureCallback<SendResult<String, KafkaNotification>>() {
                @Override
                public void onSuccess(SendResult<String, KafkaNotification> result) {
                    log.info("Sent message={}", kafkaNotification.getContent());
                }

                @Override
                public void onFailure(Throwable ex) {
                    log.error("Unable to send message={}", kafkaNotification.getContent());
                }
            });
        } catch (Exception e) {
            log.error("Kafka send error: {}", e.getMessage());
        }
    }
}
