package com.demo.restaurant_management.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface ImageService {
    String save(MultipartFile file);

    Resource load(String filename);

    Stream<Path> loadAll();
}
