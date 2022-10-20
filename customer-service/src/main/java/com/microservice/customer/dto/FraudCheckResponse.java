package com.microservice.customer.dto;

import lombok.Data;

@Data
public class FraudCheckResponse {
    private boolean isFraudster;
}
