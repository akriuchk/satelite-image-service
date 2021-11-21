package com.akriuchk.imageservice.service;

import com.akriuchk.imageservice.exception.ParsingFailedException;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class Parser {

    /**
     * Parse specified json file to list of entites with only required fields.
     * If entity on json cannot be mapped to object, it will be skipped
     *
     * @param dataSource string path to json file
     * @return List of parsed objects with required fields
     */

    public List<PreFeature> parse(String dataSource) {
        log.info("Start parsing '{}' file", dataSource);


        final File jsonFile;
        try {
            jsonFile = findFile(dataSource);
        } catch (FileNotFoundException e) {
            log.error("Invalid path to data source file");
            return Collections.emptyList();
        }

        final JsonFactory jfactory = new JsonFactory();
        final ObjectMapper mapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        try (JsonParser jParser = jfactory.createParser(jsonFile)) {
            if (jParser.nextToken() != JsonToken.START_ARRAY) {
                throw new UnsupportedOperationException("Only json arrays supported");
            }

            List<PreFeature> parsedFeatures = new ArrayList<>();
            while (jParser.nextToken() != JsonToken.END_ARRAY) {
                PreFeature preFeature = mapper.readValue(jParser, PreFeature.class);
                parsedFeatures.add(preFeature);
                log.info(preFeature.getFeatures().iterator().next().getProperties().id);
            }
            return parsedFeatures;
        } catch (IOException e) {
            log.error("Error while parsing '{}' file", dataSource, e);
            throw new ParsingFailedException("Check file content", e);
        }
    }

    private File findFile(String dataSource) throws FileNotFoundException {
        final File jsonFile = ResourceUtils.getFile(dataSource);

        if (!jsonFile.exists()) {
            throw new FileNotFoundException();
        }
        return jsonFile;
    }

    @Data
    public static class PreFeature {
        private List<InFeature> features;

        @Data
        public static class InFeature {
            private InProperty properties;

            @Data
            public static class InProperty {
                private String id;
                private Long timestamp;
                private Acquisition acquisition;
                private String quicklook;

                @Data
                public static class Acquisition {
                    private Long beginViewingDate;
                    private Long endViewingDate;
                    private String missionName;
                }
            }
        }
    }
}
