package com.microservice.customer.dto;

import lombok.Data;

@Data
public class NotificationRequest {
    private String mail;
    private String title;
    private String content;
}
