package com.akriuchk.imageservice.repository;

import com.akriuchk.imageservice.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FeatureRepository extends JpaRepository<Feature, Long> {
    Feature findByUuid(UUID id);
}
