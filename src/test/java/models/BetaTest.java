package models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BetaTest {

    // Positive Test Cases
    @Test
    @DisplayName("Test Beta generation returns 'Beta'")
    void testBeta_getGeneration_ReturnsBeta() {
        // Arrange
        Location location = Location.A;
        int yearsActive = 5;
        Beta betaRecycling = new Beta(location, yearsActive);

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
        Beta betaRecycling = new Beta(location, yearsActive);

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
        Beta betaRecycling = new Beta(location, yearsActive);

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
        Beta betaRecycling = new Beta(location, yearsActive);

        // Act
        Location actualLocation = betaRecycling.getLocation();

        // Assert
        assertEquals(Location.A, actualLocation, "The location should return 'A'.");
    }

    // Negative Test Cases
    @Test
    @DisplayName("Test negative yearsActive throws exception")
    void testBeta_InvalidYearsActive_ThrowsException() {
        // Arrange
        Location location = Location.C;
        int invalidYearsActive = -5;

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Beta(location, invalidYearsActive);
        });
    }

    @Test
    @DisplayName("Test null location throws exception")
    void testBeta_NullLocation_ThrowsException() {
        // Arrange
        Location location = null;
        int yearsActive = 5;

        // Act & Assert
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new Beta(location, yearsActive);
        });
    }

    @Test
    @DisplayName("Test Beta rates initialization is valid")
    void testBeta_RatesInitialization_ValidRates() {
        // Arrange
        Location location = Location.B;
        int yearsActive = 3;

        // Act
        Beta betaRecycling = new Beta(location, yearsActive);
        List<Double> rates = betaRecycling.getRates();

        // Assert
        assertNotNull(rates, "Rates should not be null.");
        assertFalse(rates.isEmpty(), "Rates should not be empty.");
        assertTrue(rates.stream().allMatch(rate -> rate > 0), "Rates should be greater than 0.");
    }

    @Test
    @DisplayName("Test Beta constructor handles yearsActive (zero)")
    void testBeta_YearsActive_ZeroBoundary() {
        // Arrange
        Location location = Location.C;
        int yearsActive = 0;

        // Act
        Beta betaRecycling = new Beta(location, yearsActive);

        // Assert
        assertEquals(0, betaRecycling.getYearsActive(), "Years active should handle zero.");
    }

    @Test
    @DisplayName("Test Beta constructor does not accept yearsActive greater than 100")
    void testBeta_YearsActive_ExceedsMaxBoundary_ThrowsException() {
        // Arrange
        Location location = Location.B;
        int invalidYearsActive = 101; // Invalid yearsActive exceeding the maximum of 100

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Beta(location, invalidYearsActive);
        });
    }

    @Test
    @DisplayName("Test Beta handles invalid location")
    void testBeta_Location_Invalid() {
        // Arrange
        String invalidLocation = "D"; // Invalid location outside A, B, C
        int yearsActive = 3;

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Beta(Location.valueOf(invalidLocation), yearsActive);
        });

        assertEquals("Invalid location provided.", exception.getMessage());
    }
}
