package com.example.demo_crud_spring.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String store(MultipartFile file);

    void delete(String filename);
}
