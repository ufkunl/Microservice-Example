package com.microservice.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "fraud-service")
public interface FraudClient {

    @GetMapping("/check/{customerId}")
    FraudCheckResponse isFraudulentCustomer(@PathVariable("customerId") String customerId);

}
