package com.microservice.coreservice.enums;

import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RestResponseCode implements Serializable {

    public static final RestResponseCode SUCCESS = new RestResponseCode("0", "result.info.success");
    public static final RestResponseCode ERROR = new RestResponseCode("-1", "result.info.error");
    public static final RestResponseCode FRAUD_INTEGRATION_ERROR = new RestResponseCode("10001", "password.change.error");
    public static final RestResponseCode FRAUD_CUSTOMER_ERROR = new RestResponseCode("10002", "password.change.error");
    public static final RestResponseCode CUSTOMER_NOT_FOUND = new RestResponseCode("10003", "customer.not.found");
    public static final RestResponseCode FRAUD_INFO_NOT_FOUND = new RestResponseCode("10004", "fraud.info.not.found");

    private String code;
    private String message;

}