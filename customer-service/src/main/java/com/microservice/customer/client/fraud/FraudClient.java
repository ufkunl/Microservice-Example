package com.microservice.customer.client.fraud;

import com.microservice.customer.dto.FraudChangeRequest;
import com.microservice.customer.dto.FraudCheckResponse;

public interface FraudClient {

    FraudCheckResponse isFraudster(String email);

    Void changeFraudStatus(FraudChangeRequest fraudChangeRequest);

}
