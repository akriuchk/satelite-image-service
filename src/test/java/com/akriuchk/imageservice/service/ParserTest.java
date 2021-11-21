package com.akriuchk.imageservice.service;

import com.akriuchk.imageservice.exception.ParsingFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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


}