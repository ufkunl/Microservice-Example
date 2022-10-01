package com.microservice.customer.client.fraud.domain;

import com.microservice.coreservice.enums.RestResponseCode;
import lombok.Data;

import java.io.Serializable;

@Data
public class FraudCheckClientResponse implements Serializable {

    private RestResponseCode resultCode;
    private FraudCheckClientDetailResponse data;

}
