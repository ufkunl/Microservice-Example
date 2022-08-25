package com.microservice.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public record CustomerController(CustomerService customerService) {

    @PostMapping
    public void registerCustomer(@RequestBody CustomerRegistrationRequest customerRegistrationRequest){
        customerService.registerCustomer(customerRegistrationRequest);
    }

    @GetMapping("/v1/get")
    public void getCustomer(){
        log.info("customer service called");
    }

}
