package com.world.event_service.notification;

import java.util.List;

import lombok.Builder;

@Builder
public record NotificationRequest(
    String eventTitle,
    NotificationType type,
    List<Integer> userIDs
) {
}
