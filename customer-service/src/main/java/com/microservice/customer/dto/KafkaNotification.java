package com.microservice.customer.dto;

import lombok.Data;

@Data
public class KafkaNotification {

    private String title;
    private String content;
    private String email;

}
