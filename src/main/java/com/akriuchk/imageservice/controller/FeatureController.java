package com.akriuchk.imageservice.controller;

import com.akriuchk.imageservice.client.api.delegate.FeatureApiDelegate;
import com.akriuchk.imageservice.client.model.Feature;
import com.akriuchk.imageservice.service.FeatureService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class FeatureController implements FeatureApiDelegate {

    private final FeatureService featureService;
    private final ModelMapper mapper;

    @Override
    public ResponseEntity<Feature> getFeature(String id) {
        return ResponseEntity.ok(mapper.map(featureService.getFeature(id), Feature.class));
    }

    @Override
    public ResponseEntity<List<Feature>> getFeatures() {
        final List<Feature> featureList = featureService.fetchFeatures()
                .stream()
                .map(source -> mapper.map(source, Feature.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(featureList);
    }

    @Override
    public ResponseEntity<Resource> getQuicklook(String id) {
        return FeatureApiDelegate.super.getQuicklook(id);
    }
}
