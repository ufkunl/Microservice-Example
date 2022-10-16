package com.microservice.customer.client.fraud;

import com.microservice.coreservice.dto.RestResponse;
import com.microservice.coreservice.enums.RestResponseCode;
import com.microservice.customer.dto.FraudChangeRequest;
import com.microservice.customer.dto.FraudCheckRequest;
import com.microservice.customer.dto.FraudCheckResponse;
import com.microservice.customer.exception.CustomerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FraudClientImpl implements FraudClient {

    private final FraudFeignClient fraudClient;
    private static final String FRAUD_INTEGRATION_ERROR = "FRAUD Integration Error ";

    public FraudClientImpl(FraudFeignClient fraudClient) {
        this.fraudClient = fraudClient;
    }

    @Override
    public FraudCheckResponse isFraudster(String email) {
        FraudCheckRequest request = new FraudCheckRequest();
        request.setEmail(email);
        try {
            RestResponse<FraudCheckResponse> response = fraudClient.isFraudster(request);
            if (response.getResultCode().getCode().equals(RestResponseCode.SUCCESS.getCode())) {
                return response.getData();
            } else {
                throw new CustomerException(RestResponseCode.FRAUD_INTEGRATION_ERROR);
            }
        }catch (CustomerException e){
            log.error(FRAUD_INTEGRATION_ERROR, e);
            throw e;
        }catch (Exception e) {
            log.error(FRAUD_INTEGRATION_ERROR, e);
            throw new CustomerException(RestResponseCode.FRAUD_INTEGRATION_ERROR);
        }
    }

    @Override
    public Void changeFraudStatus(FraudChangeRequest fraudChangeRequest) {
        try{
            RestResponse<Void> response = fraudClient.changeFraudStatus(fraudChangeRequest);
            if (response.getResultCode().getCode().equals(RestResponseCode.SUCCESS.getCode())) {
                return response.getData();
            } else {
                throw new CustomerException(RestResponseCode.FRAUD_INTEGRATION_ERROR, response.getResultCode().getMessage());
            }
        }catch (CustomerException e){
            log.error(FRAUD_INTEGRATION_ERROR, e);
            throw e;
        }catch (Exception e) {
            log.error(FRAUD_INTEGRATION_ERROR, e);
            throw new CustomerException(RestResponseCode.FRAUD_INTEGRATION_ERROR);
        }
    }
}
