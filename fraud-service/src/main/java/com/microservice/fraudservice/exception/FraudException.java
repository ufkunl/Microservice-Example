package com.microservice.fraudservice.exception;

import com.microservice.coreservice.enums.RestResponseCode;

public class FraudException extends Exception{

    private final RestResponseCode restResponseCode;

    public FraudException(RestResponseCode restResponseCode) {
        super(restResponseCode.getMessage());
        this.restResponseCode = restResponseCode;
    }

    public FraudException(RestResponseCode restResponseCode,String message) {
        super(message);
        this.restResponseCode = restResponseCode;
    }

    public RestResponseCode getRestResponseCode() {
        return this.restResponseCode;
    }
}
