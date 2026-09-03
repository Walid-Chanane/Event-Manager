package com.world.notification_service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("notif")
@RequiredArgsConstructor
@Tag(name = "Notification")
public class NotificationController {
    
    private final NotificationService notificationService;

    @Operation(summary = "Create notification")
    @PostMapping("send")
    public ResponseEntity<Void> addNotification(@RequestBody NotificationRequest request){
        notificationService.addNotification(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get all notifications")
    @GetMapping("get")
    public ResponseEntity<List<NotificationResponse>> getNotifications(Authentication authenticatedUser){
        return ResponseEntity.ok(notificationService.getUserNotifications(authenticatedUser));
    }

    @Operation(summary = "Mark notification as read")
    @PatchMapping("/read/{notificationId}")
    public ResponseEntity<Void> updateReadStatus(@PathVariable Integer notificationId, Authentication authenticatedUser){
        notificationService.updateReadStatus(notificationId, authenticatedUser);
        return ResponseEntity.noContent().build();
    }
}
