package com.microservice.customer.client.fraud;

import com.microservice.coreservice.dto.RestResponse;
import com.microservice.customer.dto.FraudChangeRequest;
import com.microservice.customer.dto.FraudCheckRequest;
import com.microservice.customer.dto.FraudCheckResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="fraud-service")
public interface FraudFeignClient {

    @PostMapping("/check")
    RestResponse<FraudCheckResponse> isFraudster(@RequestBody FraudCheckRequest request);

    @PostMapping("/status")
    RestResponse<Void> changeFraudStatus(@RequestBody FraudChangeRequest fraudChangeRequest);
}
