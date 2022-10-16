package com.microservice.customer.exception;

import com.microservice.coreservice.dto.RestResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerException.class)
    public RestResponse<String> handleExceptions(CustomerException exception, WebRequest webRequest) {
        return new RestResponse<>(exception.getRestResponseCode());
    }

}
