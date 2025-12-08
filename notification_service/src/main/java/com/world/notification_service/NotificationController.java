package com.world.notification_service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("notif")
@RequiredArgsConstructor
public class NotificationController {
    
    private final NotificationService notificationService;

    @PostMapping("send")
    public ResponseEntity<?> addNotification(@RequestBody NotificationRequest request){
        notificationService.addNotification(request);
        return ResponseEntity.ok().build();
    }
}
