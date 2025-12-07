package com.world.event_service.event_file;

import lombok.Builder;

@Builder
public record EventFileResponse (
    Integer id,
    String fileName,
    String filePath,
    String contentType
){}
