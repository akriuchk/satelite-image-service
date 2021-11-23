package com.akriuchk.imageservice.service;

import com.akriuchk.imageservice.repository.FeatureRepository;
import com.akriuchk.imageservice.repository.ImageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(
        classes = {FeatureImportService.class},
        properties = {
                "spring.jpa.properties.hibernate.jdbc.batch_size=2"
        }
)
class FeatureImportServiceTest {

    @Autowired
    private FeatureImportService importService;

    @MockBean
    private FeatureRepository featureRepository;
    @MockBean
    private ImageRepository imageRepository;

    @Test
    void importShouldSaveEntriesInBatches() {
        final Stream<Parser.PreFeature> objectStream = Stream.of(
                testPrefeature(true),
                testPrefeature(true),
                testPrefeature(true),
                testPrefeature(true)
        );

        importService.importFeatures(objectStream);

        verify(featureRepository, times(2)).saveAll(any());
        verify(imageRepository, times(2)).saveAll(any());
    }

    @Test
    void importShouldSaveLeftovers() { //  5/2 => 3 times
        final Stream<Parser.PreFeature> objectStream = Stream.of(
                testPrefeature(true),
                testPrefeature(true),
                testPrefeature(true),
                testPrefeature(true),
                testPrefeature(true)
        );

        importService.importFeatures(objectStream);

        verify(featureRepository, times(3)).saveAll(any());
        verify(imageRepository, times(3)).saveAll(any());
    }

    @Test
    void importShouldSkipNullImages() {
        final Stream<Parser.PreFeature> objectStream = Stream.of(
                testPrefeature(true),
                testPrefeature(true),
                testPrefeature(false),
                testPrefeature(false)
        );

        importService.importFeatures(objectStream);

        verify(featureRepository, times(2)).saveAll(any());
        verify(imageRepository, times(2)).saveAll(any());
    }

    @Test
    void closedStream() {
        final Stream<Parser.PreFeature> objectStream = Stream.of(
                testPrefeature(true));

        importService.importFeatures(objectStream);

        assertThrows(IllegalStateException.class, () -> {
            assertThat(objectStream).isEmpty();
        });

    }

    private Parser.PreFeature testPrefeature(boolean isQuicklookPresent) {
        return new Parser.PreFeature()
                .setFeatures(Collections.singletonList(new Parser.PreFeature.InFeature()
                        .setProperties(new Parser.PreFeature.InFeature.InProperty()
                                .setId(UUID.randomUUID().toString())
                                .setTimestamp(ThreadLocal.withInitial(() -> 1).get().longValue())
                                .setQuicklook(isQuicklookPresent ? "asdbasbd" : null)
                                .setAcquisition(new Parser.PreFeature.InFeature.InProperty.Acquisition()
                                        .setBeginViewingDate(ThreadLocal.withInitial(() -> 1).get().longValue())
                                        .setEndViewingDate(ThreadLocal.withInitial(() -> 1).get().longValue())
                                        .setMissionName("Test-mission")))));
    }
}