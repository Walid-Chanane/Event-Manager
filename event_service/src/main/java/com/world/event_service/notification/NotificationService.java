package com.world.event_service.notification;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {
    
    public void sendNotification(NotificationRequest request, String authHeader){

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authHeader);
        
        HttpEntity<NotificationRequest> entity = new HttpEntity<>(request, headers);
        
        restTemplate.postForObject(
            "http://localhost:8082/notif/send", 
            entity,
            Void.class
        );
    }
}
