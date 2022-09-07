package com.microservice.notificationservice.consumer;

import com.microservice.notificationservice.dto.KafkaNotification;
import com.microservice.notificationservice.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationConsumer {

    private final NotificationService notificationService;

    public NotificationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(
            topics = "${notification.kafka.topic}",
            groupId = "${notification.kafka.group.id}"
    )
    public void listen(@Payload KafkaNotification kafkaNotification) {
        log.info("Received Message: " +
                "MessageInfo : {}", kafkaNotification);
        notificationService.sendNotification(kafkaNotification);
    }
}
