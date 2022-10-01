package com.microservice.coreservice.dto;

import com.microservice.coreservice.enums.RestResponseCode;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RestResponse<T> {

    private RestResponseCode resultCode;
    private transient T data;

//    public RestResponse(RestResponseCode resultCode, T data) {
//        this.resultCode = resultCode;
//        this.data = data;
//    }
}

