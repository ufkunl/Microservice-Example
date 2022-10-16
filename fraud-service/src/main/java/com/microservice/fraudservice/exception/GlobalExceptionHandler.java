package com.microservice.fraudservice.exception;

import com.microservice.coreservice.dto.RestResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FraudException.class)
    public RestResponse<String> handleExceptions(FraudException exception, WebRequest webRequest) {
        return new RestResponse<>(exception.getRestResponseCode());
    }

}
