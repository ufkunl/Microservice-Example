package com.microservice.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, RestTemplate restTemplate) {
        this.customerRepository = customerRepository;
        this.restTemplate = restTemplate;
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

        FraudCheckResponse response = restTemplate.getForObject("http://localhost:8080/fraud/check/{customerId}",
                FraudCheckResponse.class, customer.getId());
        if (response != null && response.getIsFraudster()){
            throw new IllegalStateException("fraudster");
        }
        //todo: send notification
    }
}
