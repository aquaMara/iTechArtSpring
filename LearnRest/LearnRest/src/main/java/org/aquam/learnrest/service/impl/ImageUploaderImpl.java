package org.aquam.learnrest.service.impl;

import org.aquam.learnrest.service.ImageUploader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageUploaderImpl implements ImageUploader {
    @Override
    public String uploadImage(MultipartFile file, String uploadDirectory) throws IOException {
        StringBuilder filename = new StringBuilder();
        filename.append(file.getOriginalFilename());
        Path filenameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
        Files.write(filenameAndPath, file.getBytes());
        return filename.toString();
    }
}
