package com.microservice.fraudservice.controller;

import com.microservice.coreservice.dto.RestResponse;
import com.microservice.coreservice.dto.RestResponseHeader;
import com.microservice.coreservice.enums.RestResponseCode;
import com.microservice.fraudservice.dto.FraudChangeRequest;
import com.microservice.fraudservice.dto.FraudCheckRequest;
import com.microservice.fraudservice.dto.FraudCheckResponse;
import com.microservice.fraudservice.exception.FraudHistoryNotFoundException;
import com.microservice.fraudservice.service.FraudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class FraudController {

    private final FraudService fraudService;

    public FraudController(FraudService fraudService) {
        this.fraudService = fraudService;
    }

    @PostMapping("/check")
    public ResponseEntity<RestResponse<FraudCheckResponse>> isFraudster(@RequestBody FraudCheckRequest request) throws FraudHistoryNotFoundException {
        return new ResponseEntity<>(new RestResponse<>(RestResponseCode.SUCCESS, fraudService.isFraudulentCustomer(request.getEmail())), HttpStatus.OK) ;
    }

    @PostMapping("/status")
    public ResponseEntity<String> changeFraudStatus(@RequestBody FraudChangeRequest fraudChangeRequest) {
        fraudService.changeFraudStatus(fraudChangeRequest.getEmail(),fraudChangeRequest.getIsFraud());
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/v1/get")
    public void getFraud(){
        log.info("fraud service called");
    }

}
