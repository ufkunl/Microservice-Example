package com.microservice.fraudservice.exception;

public class FraudHistoryNotFoundException extends Exception{
    public FraudHistoryNotFoundException(String message) {
        super(message);
    }
}
