package com.microservice.fraudservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class FraudController {

    private final FraudService fraudService;

    public FraudController(FraudService fraudService) {
        this.fraudService = fraudService;
    }

    @GetMapping("/check/{customerId}")
    public FraudCheckResponse isFraudster(@PathVariable("customerId") String customerId) {
        boolean isFraudster = fraudService.isFraudulentCustomer(customerId);
        return FraudCheckResponse.builder().isFraudster(isFraudster).build();
    }

    @GetMapping("/v1/get")
    public void getFraud(){
        log.info("fraud service called");
    }

}
