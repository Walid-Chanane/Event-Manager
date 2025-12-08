package com.world.notification_service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer>{

    List<Notification> findByUserId(Integer userId);
    
}
