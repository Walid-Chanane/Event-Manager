package com.world.notification_service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void addNotification(NotificationRequest request) {

        List<Notification> notifications = request.userIDs().stream()
            .map(userId -> Notification.builder()
                .title(request.title())
                .message(request.message())
                .type(request.type())
                .userId(userId)
                .isRead(false)
                .build()
            ).toList();

        notificationRepository.saveAll(notifications);
    }

    public List<NotificationResponse> getUserNotifications(Authentication authenticatedUser) {
        Integer userId = (Integer) authenticatedUser.getPrincipal();
        List<Notification> notifications = notificationRepository.findByUserId(userId);
        return notifications.stream()
            .map(notification -> NotificationResponse.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .type(notification.getType())
                .createdDate(notification.getCreatedDate())
                .build()
            ).toList();
    }
    
}
