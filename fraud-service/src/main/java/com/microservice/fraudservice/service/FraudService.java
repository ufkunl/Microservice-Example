package com.microservice.fraudservice.service;

import com.microservice.fraudservice.dto.FraudCheckResponse;
import com.microservice.fraudservice.entity.FraudCheckHistory;
import com.microservice.fraudservice.exception.FraudHistoryNotFoundException;
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

    public FraudCheckResponse isFraudulentCustomer(String email) throws FraudHistoryNotFoundException {
        FraudCheckHistory fraudCheckHistory = fraudCheckRepository.findByEmail(email);
        if(fraudCheckHistory == null){
            throw new FraudHistoryNotFoundException("Info not found.");
        }
        return FraudCheckResponse.builder().isFraudster(fraudCheckHistory.getIsFraudster()).build();
    }

    public void changeFraudStatus(String email, Boolean isFraud) {
        FraudCheckHistory fraudCheckHistory = fraudCheckRepository.findByEmail(email);
        if(fraudCheckHistory == null){
            fraudCheckHistory = new FraudCheckHistory();
            fraudCheckHistory.setIsFraudster(isFraud);
            fraudCheckHistory.setCreatedAt(LocalDateTime.now());
            fraudCheckHistory.setEmail(email);
            fraudCheckRepository.save(fraudCheckHistory);
        }
        fraudCheckHistory.setIsFraudster(isFraud);
        fraudCheckRepository.save(fraudCheckHistory);
    }

}
