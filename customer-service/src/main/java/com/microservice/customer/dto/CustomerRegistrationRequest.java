package com.microservice.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRegistrationRequest {

    private String firstName;
    private String lastName;
    private String email;

}
