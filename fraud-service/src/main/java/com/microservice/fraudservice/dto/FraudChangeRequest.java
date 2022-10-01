package com.microservice.fraudservice.dto;

import lombok.Data;

@Data
public class FraudChangeRequest {

    private String email;
    private Boolean isFraud;

}
