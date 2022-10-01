package com.microservice.customer.client.fraud;

import com.microservice.customer.client.fraud.domain.FraudCheckClientDetailResponse;

public interface FraudClient {

    FraudCheckClientDetailResponse isFraudster(String email);

}
