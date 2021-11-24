package com.akriuchk.imageservice.service;

import com.akriuchk.imageservice.model.Feature;
import com.akriuchk.imageservice.model.Image;
import com.akriuchk.imageservice.repository.FeatureRepository;
import com.akriuchk.imageservice.repository.ImageRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static com.akriuchk.imageservice.conf.Mapper.extract;
import static com.akriuchk.imageservice.conf.Mapper.map;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeatureImportService {
    private final FeatureRepository featureRepository;
    private final ImageRepository imageRepository;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size:10}")
    private Long batchSize;

    /**
     * Consumes prefeature stream and saves object in batches to db
     *
     * @param preFeatureStream object stream
     */
    @Transactional
    public void importFeatures(Stream<Parser.PreFeature> preFeatureStream) {
        Objects.requireNonNull(batchSize, "'spring.jpa.properties.hibernate.jdbc.batch_size' cannot be null");

        log.info("Import features and images to db with batch size {}", batchSize);
        Bucket bucket = new Bucket(batchSize);

        preFeatureStream
                .forEach(pre -> {
                    Feature feature = map(pre);
                    Optional.ofNullable(extract(pre))
                            .ifPresent(img -> {
                                img.setFeature(feature);
                                bucket.add(img);
                            });

                    bucket.add(feature);

                    if (bucket.isFull()) {
                        featureRepository.saveAll(bucket.getFeatures());
                        imageRepository.saveAll(bucket.getImages());

                        bucket.clear();
                    }
                });

        if (!bucket.isEmpty()) {
            featureRepository.saveAll(bucket.getFeatures());
            imageRepository.saveAll(bucket.getImages());
        }

        preFeatureStream.close();
    }

    @RequiredArgsConstructor
    private class Bucket {
        private final Long batchSize;

        @Getter
        private final Set<Feature> features = new HashSet<>();

        @Getter
        private final Set<Image> images = new HashSet<>();

        public void add(Feature feature) {
            features.add(feature);
        }

        public void add(Image img) {
            images.add(img);
        }

        public boolean isFull() {
            return features.size() == batchSize;
        }

        public boolean isEmpty() {
            return features.isEmpty();
        }

        public void clear() {
            features.clear();
            images.clear();
        }
    }
}
