package com.microservice.notificationservice.dto;

import lombok.Data;

@Data
public class NotificationRequest {
    private String email;
    private String content;
    private String title;
}
