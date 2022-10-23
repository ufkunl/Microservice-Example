package com.microservice.customer.controller;

import com.microservice.customer.dto.ComplainCustomerRequest;
import com.microservice.customer.dto.CustomerRegistrationRequest;
import com.microservice.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public record CustomerController(CustomerService customerService) {

    @PostMapping
    public void registerCustomer(@RequestBody CustomerRegistrationRequest customerRegistrationRequest){
        customerService.registerCustomer(customerRegistrationRequest);
    }

    @PostMapping("/complain")
    public ResponseEntity<String> complainCustomer(@RequestBody ComplainCustomerRequest complainCustomerRequest){
        customerService.complainCustomer(complainCustomerRequest);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
