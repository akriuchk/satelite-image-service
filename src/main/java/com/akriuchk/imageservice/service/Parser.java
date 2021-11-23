package com.akriuchk.imageservice.service;

import com.akriuchk.imageservice.exception.ParsingFailedException;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.Singular;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class Parser {

    /**
     * Parse specified json file to list of entities with only required fields.
     * If entity on json cannot be mapped to object, it will be skipped
     *
     * @param dataSource string path to json file, support Spring resource notation
     * @return List of parsed objects with required fields, or empty when file not found
     */
    public List<PreFeature> parse(String dataSource) {
        log.info("Start parsing '{}' file", dataSource);

        final FileInputStream inputStream;
        try {
            inputStream = findFile(dataSource);
        } catch (FileNotFoundException e) {
            log.error("Invalid path to data source file");
            return Collections.emptyList();
        }

        return parseFeatures(inputStream).collect(Collectors.toList());
    }

    /**
     * Consumes InputStream from jsonFile and creates stream of
     * PreFeature objects which contains only required fields
     * json file should have array of objects
     * <p>
     * Currently, stream already initialized with all entries from file
     *
     * @param jsonInputStream input stream from json file
     * @return stream with entries from file
     */
    public Stream<PreFeature> parseFeatures(InputStream jsonInputStream) {
        final JsonFactory jfactory = new JsonFactory();
        final ObjectMapper mapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        try (JsonParser jParser = jfactory.createParser(jsonInputStream)) {
            if (jParser.nextToken() != JsonToken.START_ARRAY) {
                throw new UnsupportedOperationException("Only json arrays supported");
            }

            final Stream.Builder<PreFeature> streamBuilder = Stream.builder();

            while (jParser.nextToken() != JsonToken.END_ARRAY) {
                PreFeature preFeature = mapper.readValue(jParser, PreFeature.class);
                streamBuilder.accept(preFeature);
                log.info(preFeature.getFeatures().iterator().next().getProperties().getId());
            }

            return streamBuilder
                    .build()
                    .onClose(
                            () -> {
                                try {
                                    jParser.close();
                                } catch (IOException e) {
                                    log.error("Error while closing jsonparser", e);
                                }
                            }
                    );

        } catch (IOException e) {
            log.error("Error while parsing json file", e);
            throw new ParsingFailedException("Check file content", e);
        }
    }


    /**
     * Util method to get InputStream from file datasource
     *
     * @param dataSource path to file
     * @return FileInputStream with file content
     * @throws FileNotFoundException when file not found
     */
    public FileInputStream findFile(String dataSource) throws FileNotFoundException {
        final File jsonFile = ResourceUtils.getFile(dataSource);

        if (!jsonFile.exists()) {
            throw new FileNotFoundException(dataSource + " not found");
        }

        return new FileInputStream(jsonFile);
    }

    @Data
    @Accessors(chain = true)
    public static class PreFeature {
        @Singular
        private List<InFeature> features;

        @Data
        @Accessors(chain = true)
        public static class InFeature {
            private InProperty properties;

            @Data
            @Accessors(chain = true)
            public static class InProperty {
                private String id;
                private Long timestamp;
                private Acquisition acquisition;
                private String quicklook;

                @Data
                @Accessors(chain = true)
                public static class Acquisition {
                    private Long beginViewingDate;
                    private Long endViewingDate;
                    private String missionName;
                }
            }
        }
    }
}
