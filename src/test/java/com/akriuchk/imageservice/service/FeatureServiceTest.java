package com.akriuchk.imageservice.service;

import com.akriuchk.imageservice.model.Feature;
import com.akriuchk.imageservice.repository.FeatureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

class FeatureServiceTest {

    FeatureService featureService;
    FeatureRepository mock;

    @BeforeEach
    void setUp() {
        mock = mock(FeatureRepository.class);
        featureService = new FeatureService(mock);

        when(mock.findByUuid(any())).thenReturn(new Feature());
    }

    @Test
    void fetchFeaturesTest() {
        featureService.fetchFeatures();

        verify(mock, times(1)).findAll();
    }

    @Test
    void getFeatureTest() {
        final String testUuid = UUID.randomUUID().toString();
        featureService.getFeature(testUuid);
        verify(mock).findByUuid(UUID.fromString(testUuid));
    }
}