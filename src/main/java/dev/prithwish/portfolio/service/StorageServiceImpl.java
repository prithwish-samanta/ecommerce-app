package dev.prithwish.portfolio.service;

import dev.prithwish.portfolio.exceptions.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class StorageServiceImpl implements StorageService {
    @Value("${app.image.filepath}")
    private String imageFilePath;

    @Override
    public String store(MultipartFile file) {
        try {
            String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String fileName = UUID.randomUUID() + fileExtension;
            String filePath = imageFilePath + File.separator + fileName;
            File imageFolder = new File(imageFilePath);
            if (!imageFolder.exists()) {
                imageFolder.mkdirs();
            }
            Files.copy(file.getInputStream(), Paths.get(filePath));
            return fileName;
        } catch (Exception e) {
            throw new StorageException("Failed to store file.");
        }
    }
}
