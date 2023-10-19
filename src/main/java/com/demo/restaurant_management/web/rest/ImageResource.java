package com.demo.restaurant_management.web.rest;

import com.demo.restaurant_management.model.Image;
import com.demo.restaurant_management.service.ImageService;
import com.demo.restaurant_management.web.dto.response.utils.ErrorResponse;
import com.demo.restaurant_management.web.dto.response.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/images")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ImageResource {
    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("files") MultipartFile[] files) {
        try {
            List<String> fileNames = new ArrayList<>();

            Arrays.stream(files).forEach(file -> fileNames.add(imageService.save(file)));
            return ResponseUtils.ok(fileNames);
        } catch (Exception e) {
            return ResponseUtils.notSuccess(HttpStatus.EXPECTATION_FAILED,
                    ErrorResponse.of("err.sys.upload-error", "Upload fail"));
        }
    }

    @GetMapping("/files")
    public ResponseEntity<?> getListFiles() {
        List<Image> fileInfos = imageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(ImageResource.class, "getFile", path.getFileName().toString()).build().toString();

            var image = new Image();
            image.setTitle(filename);
            image.setImageUrl(url);
            return image;
        }).collect(Collectors.toList());

        return ResponseUtils.ok(fileInfos);
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String filename) {
        Resource file = imageService.load(filename);
        String lowercaseFilename = Objects.requireNonNull(file.getFilename()).toLowerCase();
        if (lowercaseFilename.endsWith(".jpg") ||
                lowercaseFilename.endsWith(".jpeg") ||
                lowercaseFilename.endsWith(".png") ||
                lowercaseFilename.endsWith(".gif") ||
                lowercaseFilename.endsWith(".bmp")) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/" + lowercaseFilename.substring(lowercaseFilename.lastIndexOf('.') + 1))
                    .body(file);
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                        + file.getFilename() + "\"").body(file);
    }
}
