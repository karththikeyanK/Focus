package com.gingerx.focusservice.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
@Slf4j
public class ImageUtil {

    private static final String UPLOAD_DIR = "uploads/";

    public static String saveImage(MultipartFile file, String appId) {
        // Create the upload directory if it doesn't exist
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                log.error("ImageUtil::saveImage()::Error creating upload directory: {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }

        // Get original file extension
        String originalFilename = file.getOriginalFilename();
        String extension = "";

        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        }

        // Generate a unique file name
        String fileName = appId + "_" + UUID.randomUUID() + extension;

        // Save the file
        Path filePath = uploadPath.resolve(fileName);
        try {
            Files.write(filePath, file.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            log.error("ImageUtil::saveImage()::Error saving file: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        return fileName;
    }
}
