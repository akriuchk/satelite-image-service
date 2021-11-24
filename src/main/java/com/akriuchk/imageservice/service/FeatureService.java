package com.akriuchk.imageservice.service;

import com.akriuchk.imageservice.model.Feature;
import com.akriuchk.imageservice.repository.FeatureRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FeatureService {

    private final FeatureRepository featureRepository;

    /**
     * fetch unrestricted list of features
     *
     * @return all available features
     */
    public List<Feature> fetchFeatures() {
        return featureRepository.findAll();
    }

    public Feature getFeature(String uuid) {
        return featureRepository.findByUuid(UUID.fromString(uuid));
    }

    /**
     * Looks for feature with requested uuid(public id) and returns decoded byte array with image preview
     *
     * @param uuid public key
     * @return decoded byte array
     */
    public byte[] getFeatureQuicklook(String uuid) {
        final byte[] content = featureRepository.findByUuid(UUID.fromString(uuid)).getImage().getContent();
        return Base64.getDecoder().decode(content);
    }
}
