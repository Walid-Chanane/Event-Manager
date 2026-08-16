package com.world.notification_service;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NotificationRequest(
    @NotBlank(message = "Event title is required!")
    String eventTitle,

    @NotNull(message="Notification type is requried!")
    NotificationType type,

    @NotEmpty(message = "List of participants must not be empty!")
    List<Integer> userIDs
) {}
