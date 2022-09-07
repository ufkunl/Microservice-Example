package com.microservice.fraudservice.service;

import com.microservice.fraudservice.entity.FraudCheckHistory;
import com.microservice.fraudservice.repository.FraudCheckRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class FraudService {

    private final FraudCheckRepository fraudCheckRepository;

    @Autowired
    public FraudService(FraudCheckRepository fraudCheckRepository) {
        this.fraudCheckRepository = fraudCheckRepository;
    }

    public boolean isFraudulentCustomer(String customerId){
        fraudCheckRepository.save(FraudCheckHistory.builder()
                .customerId(customerId)
                .isFraudster(false)
                .createdAt(LocalDateTime.now()).build());
        log.info("fraud check request for customer {}",customerId);
        return false;
    }

}
