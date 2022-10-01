package com.microservice.coreservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microservice.coreservice.enums.RestResponseCode;
import lombok.Data;

import java.io.Serializable;

@Data
public class RestResponseHeader implements Serializable {
    private RestResponseCode resultCode;
    private String resultMessage;

    public RestResponseHeader(@JsonProperty("resultCode") RestResponseCode resultCode) {
        this.resultCode = resultCode;
        this.resultMessage = resultCode.getMessage();
    }

    public RestResponseHeader(@JsonProperty("resultCode") RestResponseCode resultCode, @JsonProperty("resultMessage") String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }
}
