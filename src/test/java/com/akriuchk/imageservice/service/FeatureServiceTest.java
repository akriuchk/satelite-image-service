package com.akriuchk.imageservice.service;

import com.akriuchk.imageservice.model.Feature;
import com.akriuchk.imageservice.model.Image;
import com.akriuchk.imageservice.repository.FeatureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    void getFeatureQuicklook() throws IOException {
        final UUID uuid = UUID.randomUUID();
        when(mock.findByUuid(uuid)).thenReturn(featureWithImage());

        final byte[] decoded = featureService.getFeatureQuicklook(uuid.toString());

        assertThat(Base64.getEncoder().encode(decoded))
                .isEqualTo(featureWithImage().getImage().getContent());
    }

    private Feature featureWithImage() throws IOException {
        final Image img = new Image();
        img.setContent(Files.readString(ResourceUtils.getFile("classpath:quicklook-base64.txt").toPath()).getBytes(StandardCharsets.UTF_8));
        final Feature feature = new Feature();
        feature.setImage(img);

        return feature;
    }
}