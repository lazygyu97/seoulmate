package com.sparta.seoulmate.repository;

import com.sparta.seoulmate.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image,Long> {
    boolean existsByImageUrlAndId(String storedFileName, Long id);

    Optional<Image> findByUserId(Long id);
}
