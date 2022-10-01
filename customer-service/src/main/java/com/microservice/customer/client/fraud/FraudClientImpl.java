package com.microservice.customer.client.fraud;

import com.microservice.coreservice.dto.RestResponse;
import com.microservice.coreservice.enums.RestResponseCode;
import com.microservice.customer.client.fraud.domain.FraudCheckClientDetailResponse;
import com.microservice.customer.client.fraud.domain.FraudCheckClientRequest;
import com.microservice.customer.client.fraud.domain.FraudCheckClientResponse;
import com.microservice.customer.exception.CustomerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class FraudClientImpl implements FraudClient {

    @Autowired
    private RestTemplate restTemplate;

    private static final String url = "http://localhost:8080/fraud";
    private static final String CHECK_FRAUD = "/check";

    @Override
    public FraudCheckClientDetailResponse isFraudster(String email) {
        try {
            FraudCheckClientRequest request = new FraudCheckClientRequest();
            request.setEmail(email);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<FraudCheckClientRequest> requestEntity = new HttpEntity<>(request, headers);

            ResponseEntity<RestResponse<FraudCheckClientDetailResponse>> response = restTemplate.exchange(url + CHECK_FRAUD, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<>() {});

            if (response.getBody().getResultCode().equals(RestResponseCode.SUCCESS.getCode()))
                throw new CustomerException(RestResponseCode.CUSTOMER_FRAUD_ERROR);

            return response.getBody().getData();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomerException(RestResponseCode.CUSTOMER_FRAUD_ERROR);
        }
    }
}
