package com.starshipshop.starshipservice.service.mapper.converter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(SpringExtension.class)
public class IdToHashConverterTest {

    private static final String HASHIDS_SALT_KEY = "mySuperTestSalt";

    private IdToHashConverter idToHashConverter;

    @BeforeEach
    void setup() {
        idToHashConverter = new IdToHashConverter();
        ReflectionTestUtils.setField(idToHashConverter, "HASHIDS_SALT_KEY", HASHIDS_SALT_KEY);
    }

    @Test
    @DisplayName("Given a valid id when converted should return the according hash")
    void givenAValidId_whenConverted_shouldReturnTheAccordingHash() {
        // GIVEN
        Long id = 1L;

        // WHEN
        String hash = idToHashConverter.convert(id);

        // THEN
        assertEquals("W5pvAw0r", hash);
    }

    @Test
    @DisplayName("Given a null id when converted should throw an IllegalArgumentException")
    void givenANulldHash_whenConverted_shouldThrowIllegalArgumentException() {
        // GIVEN
        Long id = null;

        // WHEN THEN
        assertThrows(IllegalArgumentException.class, () -> idToHashConverter.convert(id));
    }

    @Test
    @DisplayName("Given an id when converted hash should be greater than or equals to size 8")
    void givenAInvalidHash_whenConverted_shouldThrowException() {
        // GIVEN
        Long id = 1L;

        // WHEN
        String hash = idToHashConverter.convert(id);

        // THEN
        assertEquals(true, hash.length() >= 8);
    }
}
