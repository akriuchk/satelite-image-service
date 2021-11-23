package com.akriuchk.imageservice.service;

import com.akriuchk.imageservice.exception.ParsingFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {

    private Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Parser();
    }

    @Test
    void parsingNonExistingFileShouldReturnEmptyList() {
        final List<Parser.PreFeature> parse = parser.parse("");
        assertThat(parse).isEmpty();

    }

    @Test
    void parsingJsonArrayShouldReturn2Entities() {
        final List<Parser.PreFeature> parse = parser.parse("classpath:test-array.json");
        assertThat(parse).hasSize(2);
    }

    @Test
    void parsingCorruptedJsonShouldThrowError() {
        assertThrows(ParsingFailedException.class,
                () -> parser.parse("classpath:test-1-good-entity.json"));
    }


    @Test
    void parseFeatures() throws Exception{
        final FileInputStream inputStream = parser.findFile("classpath:test-array.json");
        final Stream<Parser.PreFeature> preStream = parser.parseFeatures(inputStream);

        assertThat(preStream).hasSize(2);
    }

    @Test
    void shouldReturnFileinputStreamWhenFileisPresent() {
        assertDoesNotThrow(() -> {
            final FileInputStream inputStream = parser.findFile("classpath:test-array.json");
            assertThat(inputStream).isNotEmpty();
        });
    }

    @Test
    void shouldThrowExceptionWhenFilenotfound() {
        assertThrows(FileNotFoundException.class,
                () -> parser.findFile("classpath:a"));
    }
}