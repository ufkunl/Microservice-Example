package com.microservice.customer.dto;

import lombok.Data;

@Data
public class FraudChangeRequest {

    private Boolean isFraud;
    private String email;

}
