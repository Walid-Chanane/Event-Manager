package com.world.event_service.event_file;

import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
@Slf4j
public class FileStorageService {

    @Value("${application.file.upload.path}")
    private String fileUploadPath;
    
    public String saveFile(@Nonnull MultipartFile file, @Nonnull Integer eventId) {
        
        final Path finalUploadPath = getPath(file, eventId);
        try {
            Files.createDirectories(finalUploadPath);
        } catch (IOException e) {
            log.error("Failed to create directory: {}", finalUploadPath, e);
            return null;
        }
        
        final String fileExtension = getFileExtension(file.getOriginalFilename());
        String fileName = UUID.randomUUID().toString() + "." + fileExtension;
        Path targetPath = finalUploadPath.resolve(fileName);
        
        try {
            file.transferTo(targetPath);
            return targetPath.toString();
        } catch (IOException e) {
            log.error("Failed to write file: {}", targetPath, e);
        }
        return null;
    }
    
    private Path getPath(@Nonnull MultipartFile file,@Nonnull Integer eventId) {
        
        final String fileExtension = getFileExtension(file.getOriginalFilename());
        
        String extensionSubPath = switch (fileExtension) {
            case "jpg", "jpeg", "png", "gif" -> "images";
            case "pdf", "doc", "docx" -> "documents";
            case "mp4", "mov", "avi" -> "videos";
            default -> "others";
        };

        return Path.of(fileUploadPath, "event_" + eventId, extensionSubPath);
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return "";
        }
        return fileName.substring(lastDotIndex + 1).toLowerCase();
    }
    
}