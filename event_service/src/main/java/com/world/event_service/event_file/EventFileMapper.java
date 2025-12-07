package com.world.event_service.event_file;

import org.springframework.stereotype.Service;

@Service
public class EventFileMapper {
    
    public EventFileResponse toResponse(EventFile file){
        return EventFileResponse.builder()
            .id(file.getId())
            .fileName(file.getFileName())
            .filePath(file.getFilePath())
            .contentType(file.getContentType())
            .build();
    }
}
