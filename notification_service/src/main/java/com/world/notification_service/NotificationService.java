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
        String title = switch (request.type()) {
            case EVENT_REGISTRATION -> "Registration successful";
            case EVENT_WITHDRAWAL -> "Withdrawal successful";
            case EVENT_CANCELLED -> "Event cancelled";
            case EVENT_MODIFIED -> "Event updated";
        };

        String message = switch (request.type()) {
            case EVENT_REGISTRATION -> "You have been successfully registered for the event '" + request.eventTitle() + "'.";
            case EVENT_WITHDRAWAL -> "You have been successfully unregistered from the event '" + request.eventTitle() + "'.";
            case EVENT_CANCELLED -> "The event '" + request.eventTitle() + "' has been cancelled.";
            case EVENT_MODIFIED -> "The event '" + request.eventTitle() + "' has been modified.";
        };
        
        List<Notification> notifications = request.userIDs().stream()
            .map(userId -> Notification.builder()
                .title(title)
                .message(message)
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
                .isRead(notification.isRead())
                .type(notification.getType())
                .createdDate(notification.getCreatedDate())
                .build()
            ).toList();
    }

    public void updateReadStatus(Integer notificationId, Authentication authenticatedUser) {
        Integer userId = (Integer) authenticatedUser.getPrincipal();
        Notification notification = notificationRepository.findByIdAndUserId(notificationId, userId);
        notification.setRead(true);
        notificationRepository.save(notification);
    }
    
}
