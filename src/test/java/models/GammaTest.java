package models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GammaTest {
    private Gamma gammaRecycling;

    @Test
    @DisplayName("Test Gamma generation returns 'Gamma'")
    void testGamma_getGeneration_ReturnsGamma() {
        // Arrange
        Location location = Location.A;
        int yearsActive = 10;
        gammaRecycling = new Gamma(location, yearsActive);

        // Act
        String generation = gammaRecycling.getGeneration();

        // Assert
        assertEquals("Gamma", generation, "The generation should be 'Gamma'.");
    }

    @Test
    @DisplayName("Test Gamma rates return correct rates")
    void testGamma_getRates_ReturnsCorrectRates() {
        // Arrange
        Location location = Location.B;
        int yearsActive = 5;
        gammaRecycling = new Gamma(location, yearsActive);

        // Act
        List<Double> rates = gammaRecycling.getRates();

        // Assert
        assertEquals(List.of(1.5, 2.0, 3.0), rates, "The rates for Gamma should be [1.5, 2.0, 3.0].");
    }

    @Test
    @DisplayName("Test Gamma yearsActive returns correct year value")
    void testGamma_getYearsActive_ReturnsCorrectYears() {
        // Arrange
        Location location = Location.C;
        int yearsActive = 15;
        gammaRecycling = new Gamma(location, yearsActive);

        // Act
        int actualYearsActive = gammaRecycling.getYearsActive();

        // Assert
        assertEquals(15, actualYearsActive, "The yearsActive should return 15.");
    }

    @Test
    @DisplayName("Test Gamma location returns correct location")
    void testGamma_getLocation_ReturnsCorrectLocation() {
        // Arrange
        Location location = Location.C;
        int yearsActive = 20;
        gammaRecycling = new Gamma(location, yearsActive);

        // Act
        Location actualLocation = gammaRecycling.getLocation();

        // Assert
        assertEquals(Location.C, actualLocation, "The location should return 'C'.");
    }

    @Test
    @DisplayName("Test Gamma rates initialisation with valid rates")
    void testGamma_RatesInitialisation_ValidRates() {
        // Arrange
        Location location = Location.A;
        int yearsActive = 7;

        // Act
        gammaRecycling = new Gamma(location, yearsActive);
        List<Double> rates = gammaRecycling.getRates();

        // Assert
        assertNotNull(rates, "Rates should not be null.");
        assertFalse(rates.isEmpty(), "Rates should not be empty.");
        assertTrue(rates.stream().allMatch(rate -> rate >= 0), "Rates should not contain negative values.");
    }

    @Test
    @DisplayName("Test Gamma constructor with invalid yearsActive throws exception")
    void testGamma_InvalidYearsActive_ThrowsException() {
        // Arrange
        Location location = Location.B;
        int invalidYearsActive = -5;

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Gamma(location, invalidYearsActive);
        });

        assertEquals("Years active cannot be negative.", exception.getMessage());
    }

    @Test
    @DisplayName("Test Gamma constructor with null location throws exception")
    void testGamma_NullLocation_ThrowsException() {
        // Arrange
        Location location = null;
        int yearsActive = 8;

        // Act & Assert
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new Gamma(location, yearsActive);
        });

        assertEquals("Location cannot be null.", exception.getMessage());
    }
}