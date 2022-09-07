package com.microservice.notificationservice.service;

import com.microservice.notificationservice.dto.KafkaNotification;
import com.microservice.notificationservice.entity.Notification;
import com.microservice.notificationservice.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void sendNotification(KafkaNotification kafkaNotification) {
        Notification notification = Notification.builder()
                .email(kafkaNotification.getEmail())
                .content(kafkaNotification.getContent())
                .title(kafkaNotification.getTitle())
                .sendDate(LocalDateTime.now())
                .isSuccess(true)
                .build();
        notificationRepository.save(notification);
        log.info("Notification sended. {}",notification);
    }

}
