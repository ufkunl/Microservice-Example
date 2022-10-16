package com.microservice.coreservice.dto;

import com.microservice.coreservice.enums.RestResponseCode;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class RestResponse<T> {

    @NonNull
    private RestResponseCode resultCode;
    private T data;

    public RestResponse(@NonNull RestResponseCode resultCode) {
        this.resultCode = resultCode;
    }

}

