package com.microservice.fraudservice.controller;

import com.microservice.coreservice.dto.RestResponse;
import com.microservice.coreservice.enums.RestResponseCode;
import com.microservice.fraudservice.dto.FraudChangeRequest;
import com.microservice.fraudservice.dto.FraudCheckRequest;
import com.microservice.fraudservice.dto.FraudCheckResponse;
import com.microservice.fraudservice.exception.FraudException;
import com.microservice.fraudservice.service.FraudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class FraudController {

    private final FraudService fraudService;


    public FraudController(FraudService fraudService) {
        this.fraudService = fraudService;
    }

    @PostMapping("/check")
    public RestResponse<FraudCheckResponse> isFraudster(@RequestBody FraudCheckRequest request) throws FraudException {
        return new RestResponse<>(RestResponseCode.SUCCESS, fraudService.isFraudulentCustomer(request.getEmail()));
    }

    @PostMapping("/status")
    public RestResponse<Void> changeFraudStatus(@RequestBody FraudChangeRequest fraudChangeRequest) {
        fraudService.changeFraudStatus(fraudChangeRequest.getEmail(),fraudChangeRequest.getIsFraud());
        return new RestResponse<>(RestResponseCode.SUCCESS);
    }

}
