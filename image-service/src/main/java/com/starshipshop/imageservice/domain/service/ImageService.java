package com.starshipshop.imageservice.domain.service;

import com.starshipshop.imageservice.common.exception.ResourceNotFoundException;
import com.starshipshop.imageservice.repository.ImageRepository;
import com.starshipshop.imageservice.repository.jpa.image.Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Service
@Slf4j
public class ImageService {

    private static String ROOT_FILE = "images";
    private final ResourceLoader resourceLoader;
    private final ImageRepository imageRepository;

    public Resource findOneImage(String filename) {
        Image image = imageRepository.findByFilename(filename).orElseThrow(() -> new ResourceNotFoundException("Image not found in DB."));
        return resourceLoader.getResource("classpath:" + ROOT_FILE + "/" + image.getFilename() + "." + image.getExtension());
    }

    public void createImage(MultipartFile file) throws Exception {
        if(!file.isEmpty()) {
            Files.copy(file.getInputStream(), Paths.get(ROOT_FILE, file.getOriginalFilename()));
            imageRepository.save(Image.builder()
                    .filename(file.getOriginalFilename())
                    .uri(ROOT_FILE + "/" + file.getOriginalFilename())
                    .build());
        }
    }

    public void deleteImage(String filename) throws IOException {
        imageRepository.deleteByFilename(filename);
        log.info(filename +" has been deleted.");
        Files.deleteIfExists(Paths.get(ROOT_FILE, filename));
    }
}
