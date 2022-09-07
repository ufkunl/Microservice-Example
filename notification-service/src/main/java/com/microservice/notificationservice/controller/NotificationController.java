package com.microservice.notificationservice.controller;

import com.microservice.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

//    @PostMapping("/v1/send")
//    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest notificationRequest){
//        return ResponseEntity.ok(notificationService.sendNotification(notificationRequest));
//    }

}
