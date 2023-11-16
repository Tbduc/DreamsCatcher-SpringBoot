package com.codecool.elproyectegrande1.controller;

import com.codecool.elproyectegrande1.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081/")
@RestController
@RequestMapping("/api/v1")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value="/upload", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadImage(@RequestParam("image") MultipartFile file, @RequestParam Optional<String> dreamId, @RequestParam Optional<String> offerId) throws IOException {
        String dId = dreamId.orElse(null);
        String oId = offerId.orElse(null);
        imageService.uploadImage(file, dId, oId);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String fileName) {
        byte[] image = imageService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png, image/jpg, image/jpeg")).body(image);
    }

    @GetMapping(value ="/image/display/{id}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    @ResponseBody
    public String showImage(@PathVariable("id") Long id) {
        byte[] image = imageService.getImageById(id);
        String encodedString = Base64.getEncoder().encodeToString(image);
        return encodedString;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping(value="/update/profile-image", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadImage(@RequestParam("image") MultipartFile file, @RequestParam String userId) throws IOException {
        Long userIdLong = Long.valueOf(userId);
        imageService.updateProfileImage(file, userIdLong);
    }
}
