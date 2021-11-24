package com.akriuchk.imageservice.service;

import com.akriuchk.imageservice.model.Feature;
import com.akriuchk.imageservice.repository.FeatureRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FeatureService {

    private final FeatureRepository featureRepository;

    public List<Feature> fetchFeatures() {
        return featureRepository.findAll();
    }

    public Feature getFeature(String uuid) {
        return featureRepository.findByUuid(UUID.fromString(uuid));
    }
}
