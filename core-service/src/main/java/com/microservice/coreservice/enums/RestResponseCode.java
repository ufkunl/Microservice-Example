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
    public static final RestResponseCode REFRESH_NOT_FOUND = new RestResponseCode("10001", "refresh.not.found");
    public static final RestResponseCode ACCESS_TOKEN_INVALID = new RestResponseCode("10002", "access.token.not.found");
    public static final RestResponseCode USERNAME_OR_PASSWORD_NOT_FOUND = new RestResponseCode("10003", "username.or.password.not.found");
    public static final RestResponseCode USERNAME_OR_EMAIL_EXIST = new RestResponseCode("10004", "username.or.email.exist");
    public static final RestResponseCode USER_NOT_FOUND = new RestResponseCode("10005", "user.not.found");
    public static final RestResponseCode UPLOAD_ERROR = new RestResponseCode("10006", "upload.error");
    public static final RestResponseCode PASSWORD_CHANGE_ERROR = new RestResponseCode("10007", "password.change.error");
    public static final RestResponseCode CUSTOMER_FRAUD_ERROR = new RestResponseCode("10008", "password.change.error");

    private String code;
    private String message;

//    public RestResponseCode(String code, String message) {
//        this.code = code;
//        this.message = message;
//    }
//
//    public String getCode() {
//        return this.code;
//    }
//
//    public String getMessage() {
//        return this.message;
//    }

}