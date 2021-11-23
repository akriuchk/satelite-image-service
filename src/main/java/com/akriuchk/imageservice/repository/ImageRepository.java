package com.akriuchk.imageservice.repository;

import com.akriuchk.imageservice.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
}
