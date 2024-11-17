package models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BetaTest {

    private Beta betaRecycling;

    @Test
    @DisplayName("Test Beta generation returns 'Beta'")
    void testBeta_getGeneration_ReturnsBeta() {
        // Arrange
        Location location = Location.A;
        int yearsActive = 5;
        betaRecycling = new Beta(location, yearsActive);

        // Act
        String generation = betaRecycling.getGeneration();

        // Assert
        assertEquals("Beta", generation, "The generation should be 'Beta'.");
    }

    @Test
    @DisplayName("Test Beta rates return correct rates")
    void testBeta_getRates_ReturnsCorrectRates() {
        // Arrange
        Location location = Location.B;
        int yearsActive = 3;
        betaRecycling = new Beta(location, yearsActive);

        // Act
        List<Double> rates = betaRecycling.getRates();

        // Assert
        assertEquals(List.of(1.5, 1.5, 1.5), rates, "The rates for Beta should be [1.5, 1.5, 1.5].");
    }

    @Test
    @DisplayName("Test Beta yearsActive returns correct year value")
    void testBeta_getYearsActive_ReturnsCorrectYears() {
        // Arrange
        Location location = Location.C;
        int yearsActive = 10;
        betaRecycling = new Beta(location, yearsActive);

        // Act
        int actualYearsActive = betaRecycling.getYearsActive();

        // Assert
        assertEquals(10, actualYearsActive, "The yearsActive should return 10.");
    }

    @Test
    @DisplayName("Test Beta location returns correct location")
    void testBeta_getLocation_ReturnsCorrectLocation() {
        // Arrange
        Location location = Location.A;
        int yearsActive = 7;
        betaRecycling = new Beta(location, yearsActive);

        // Act
        Location actualLocation = betaRecycling.getLocation();

        // Assert
        assertEquals(Location.A, actualLocation, "The location should return 'A'.");
    }

    @Test
    @DisplayName("Test Beta rates initialization")
    void testBeta_RatesInitialization_ValidRates() {
        // Arrange
        Location location = Location.B;
        int yearsActive = 3;

        // Act
        betaRecycling = new Beta(location, yearsActive);
        List<Double> rates = betaRecycling.getRates();

        // Assert
        assertNotNull(rates, "Rates should not be null."); // Ensure the list is not null
        assertFalse(rates.isEmpty(), "Rates should not be empty."); // Ensure the list is not empty
        assertTrue(rates.stream().allMatch(rate -> rate >= 0), "Rates should not contain negative values.");
    }

    @Test
    @DisplayName("Test invalid yearsActive (negative test)")
    void testBeta_InvalidYearsActive_ThrowsException() {
        // Arrange
        Location location = Location.C;
        int invalidYearsActive = -5;

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Beta(location, invalidYearsActive);
        });

        assertEquals("Years active cannot be negative.", exception.getMessage());
    }

    @Test
    @DisplayName("Test null location handling (negative test)")
    void testBeta_NullLocation_ThrowsException() {
        // Arrange
        Location location = null;
        int yearsActive = 5;

        // Act & Assert
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new Beta(location, yearsActive);
        });

        assertEquals("Location cannot be null.", exception.getMessage());
    }
}