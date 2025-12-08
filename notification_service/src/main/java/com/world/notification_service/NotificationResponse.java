package com.world.notification_service;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record NotificationResponse(
    Integer id,
    String title,
    String message,
    NotificationType type,
    LocalDateTime createdDate
) {}
