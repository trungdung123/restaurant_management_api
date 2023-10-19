package com.demo.restaurant_management.service.impl;

import com.demo.restaurant_management.model.Image;
import com.demo.restaurant_management.repository.ImageRepository;
import com.demo.restaurant_management.service.ImageService;
import com.demo.restaurant_management.web.rest.ImageResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final Path root = Paths.get("src/main/resources/static/image");

    @Override
    public String save(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            String extFile = Objects.requireNonNull(fileName).substring(fileName.lastIndexOf("."));
            Image image = new Image();
            imageRepository.save(image);
            image.setTitle(image.getId().toString() + extFile);
            String url = MvcUriComponentsBuilder
                    .fromMethodName(ImageResource.class, "getFile", image.getTitle()).build().toString();
            image.setImageUrl(url);
            imageRepository.save(image);
            Files.copy(file.getInputStream(), this.root.resolve(Objects.requireNonNull(image.getTitle())));
            return image.getId().toString();
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}
