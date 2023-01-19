package com.starshipshop.starshipservice.service.mapper.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.starshipshop.starshipservice.common.exception.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
public class HashToIdConverterTest {

    private static final String HASHIDS_SALT_KEY = "mySuperTestSalt";

    private HashToIdConverter hashToIdConverter;

    @BeforeEach
    void setup() {
        hashToIdConverter = new HashToIdConverter();
        ReflectionTestUtils.setField(hashToIdConverter, "HASHIDS_SALT_KEY", HASHIDS_SALT_KEY);
    }

    @Test
    @DisplayName("Given a valid hash when converted should return the according id")
    void givenAValidHash_whenConverted_shouldReturnTheAccordingId() {
        // GIVEN
        String hash = "W5pvAw0r";

        // WHEN
        Long id = hashToIdConverter.convert(hash);

        // THEN
        assertEquals(1L, id);
    }

    @Test
    @DisplayName("Given an empty hash when converted should throw an IllegalArgumentException")
    void givenAnEmptydHash_whenConverted_shouldThrowIllegalArgumentException() {
        // GIVEN
        String hash = "";

        // WHEN THEN
        assertThrows(IllegalArgumentException.class, () -> hashToIdConverter.convert(hash));
    }

    @Test
    @DisplayName("Given a null hash when converted should throw an IllegalArgumentException")
    void givenANulldHash_whenConverted_shouldThrowIllegalArgumentException() {
        // GIVEN
        String hash = null;

        // WHEN THEN
        assertThrows(IllegalArgumentException.class, () -> hashToIdConverter.convert(hash));
    }

    @Test
    @DisplayName("Given an invalid hash when converted should throw a ResourceNotFoundException")
    void givenAInvalidHash_whenConverted_shouldThrowException() {
        // GIVEN
        String hash = "wrongHash";

        // WHEN THEN
        assertThrows(ResourceNotFoundException.class, () -> hashToIdConverter.convert(hash));
    }
}
