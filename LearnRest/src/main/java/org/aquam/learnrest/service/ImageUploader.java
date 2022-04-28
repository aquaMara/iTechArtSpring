package org.aquam.learnrest.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageUploader {

    String uploadImage(MultipartFile file, String uploadDirectory) throws IOException;
}
