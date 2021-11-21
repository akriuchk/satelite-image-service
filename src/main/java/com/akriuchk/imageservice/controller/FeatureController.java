package com.akriuchk.imageservice.controller;

import com.akriuchk.imageservice.client.api.delegate.FeatureApiDelegate;
import com.akriuchk.imageservice.client.model.Feature;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class FeatureController implements FeatureApiDelegate {

    @Override
    public ResponseEntity<Feature> getFeature(String id) {
        return ResponseEntity.of(Optional.of(new Feature()));
    }

    @Override
    public ResponseEntity<List<Feature>> getFeatures() {
        return ResponseEntity.ok(Collections.emptyList());
    }

    @Override
    public ResponseEntity<Resource> getQuicklook(String id) {
        return FeatureApiDelegate.super.getQuicklook(id);
    }
}
