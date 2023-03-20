package com.starshipshop.imageservice.repository;

import com.starshipshop.imageservice.repository.jpa.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByFilename(String filename);
    void deleteByFilename(String filename);
}
