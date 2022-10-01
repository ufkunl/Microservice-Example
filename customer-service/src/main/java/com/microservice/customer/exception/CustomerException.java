package com.microservice.customer.exception;

import com.microservice.coreservice.enums.RestResponseCode;

public class CustomerException extends RuntimeException{

    private final RestResponseCode restResponseCode;

    public CustomerException(RestResponseCode restResponseCode) {
        super(restResponseCode.getMessage());
        this.restResponseCode = restResponseCode;
    }

    public CustomerException(RestResponseCode restResponseCode,String message) {
        super(message);
        this.restResponseCode = restResponseCode;
    }

    public RestResponseCode getRestResponseCode() {
        return this.restResponseCode;
    }
}
