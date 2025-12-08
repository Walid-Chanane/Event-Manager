package com.world.notification_service;

import java.util.List;

public record NotificationRequest(
    String title,
    String message,
    NotificationType type,
    List<Integer> userIDs
) {}
