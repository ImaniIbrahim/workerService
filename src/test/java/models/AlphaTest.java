package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class AlphaTest {
    private Alpha alphaRecycling;

    @Test
    @DisplayName("Test Alpha generation returns 'Alpha'")
    void testAlpha_getGeneration_ReturnsAlpha() {
        // Arrange
        Location location = Location.A;
        int yearsActive = 5;
        alphaRecycling = new Alpha(location, yearsActive);
        // Act
        String generation = alphaRecycling.getGeneration();
        // Assert
        assertEquals("Alpha", generation, "The generation should be 'Alpha'.");
    }

    @Test
    @DisplayName("Test Alpha rates return correct rates")
    void testAlpha_getRates_ReturnsCorrectRates() {
        // Arrange
        Location location = Location.B;
        int yearsActive = 3;
        alphaRecycling = new Alpha(location, yearsActive);

        // Act
        List<Double> rates = alphaRecycling.getRates();

        // Assert
        assertEquals(List.of(1.0, 1.0, 1.0), rates, "The rates for Alpha should be [1.0, 1.0, 1.0].");
    }

    @Test
    @DisplayName("Test Alpha yearsActive returns correct year value")
    void testAlpha_getYearsActive_ReturnsCorrectYears() {
        // Arrange
        Location location = Location.C;
        int yearsActive = 10;
        alphaRecycling = new Alpha(location, yearsActive);

        // Act
        int actualYearsActive = alphaRecycling.getYearsActive();

        // Assert
        assertEquals(10, actualYearsActive, "The yearsActive should return 10.");
    }

    @Test
    @DisplayName("Test Alpha location returns correct location")
    void testAlpha_Location_ReturnsCorrectLocation() {
        // Arrange
        Location location = Location.A;
        int yearsActive = 7;
        alphaRecycling = new Alpha(location, yearsActive);

        // Act
        Location actualLocation = alphaRecycling.getLocation();

        // Assert
        assertEquals(Location.A, actualLocation, "The location should return 'A'.");
    }

    @Test
    @DisplayName("Test Alpha rates initialisation")
    void testAlpha_RatesInitialisation_ValidRates() {
        // Arrange
        Location location = Location.B;
        int yearsActive = 3;

        // Act
        alphaRecycling = new Alpha(location, yearsActive);
        List<Double> rates = alphaRecycling.getRates();

        // Assert
        assertNotNull(rates, "Rates should not be null."); // Ensure the list is not null
        assertFalse(rates.isEmpty(), "Rates should not be empty."); // Ensure the list is not empty
        assertTrue(rates.stream().allMatch(rate -> rate >= 0), "Rates should not contain negative values.");
    }


    @Test
    @DisplayName("Test invalid yearsActive (negative test)")
    void testAlpha_InvalidYearsActive_ThrowsException() {
        // Arrange
        Location location = Location.C;
        int invalidYearsActive = -5;

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Alpha(location, invalidYearsActive);
        });

        assertEquals("Years active cannot be negative.", exception.getMessage());
    }

    @Test
    @DisplayName("Test null location handling (negative test)")
    void testAlpha_NullLocation_ThrowsException() {
        // Arrange
        Location location = null;
        int yearsActive = 5;

        // Act & Assert
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new Alpha(location, yearsActive);
        });

        assertEquals("Location cannot be null.", exception.getMessage());
    }



}