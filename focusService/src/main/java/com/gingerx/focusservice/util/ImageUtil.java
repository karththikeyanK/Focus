package com.gingerx.focusservice.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Base64;
import java.util.UUID;

@Service
@Slf4j
public class ImageUtil {

    private static final String UPLOAD_DIR = "/var/uploads/";

    public static String saveImage(byte[] fileBytes, String appId) {
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

        String extension = ".png";
        String fileName = appId + "_" + UUID.randomUUID() + extension;
        Path filePath = uploadPath.resolve(fileName);
        try {
            Files.write(filePath, fileBytes, StandardOpenOption.CREATE);
        } catch (IOException e) {
            log.error("ImageUtil::saveImage()::Error saving file: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        return fileName;
    }


    public static byte[] decodeBase64Image(String base64Image) {
        log.info("ImageUtil::decodeBase64Image()::Decoding base64 image");
        return Base64.getDecoder().decode(base64Image);
    }
}
