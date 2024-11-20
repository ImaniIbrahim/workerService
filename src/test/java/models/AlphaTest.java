package models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlphaTest {

    // Positive Test Cases
    @Test
    @DisplayName("Test Alpha generation returns 'Alpha'")
    void testAlpha_getGeneration_ReturnsAlpha() {
        // Arrange
        Location location = Location.A;
        int yearsActive = 5;
        Alpha alphaRecycling = new Alpha(location, yearsActive);

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
        Alpha alphaRecycling = new Alpha(location, yearsActive);

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
        Alpha alphaRecycling = new Alpha(location, yearsActive);

        // Act
        int actualYearsActive = alphaRecycling.getYearsActive();

        // Assert
        assertEquals(10, actualYearsActive, "The yearsActive should return 10.");
    }

    @Test
    @DisplayName("Test Alpha location returns correct location")
    void testAlpha_getLocation_ReturnsCorrectLocation() {
        // Arrange
        Location location = Location.A;
        int yearsActive = 7;
        Alpha alphaRecycling = new Alpha(location, yearsActive);

        // Act
        Location actualLocation = alphaRecycling.getLocation();

        // Assert
        assertEquals(Location.A, actualLocation, "The location should return 'A'.");
    }

    // Negative Test Cases
    @Test
    @DisplayName("Test negative yearsActive throws exception")
    void testAlpha_InvalidYearsActive_ThrowsException() {
        // Arrange
        Location location = Location.C;
        int invalidYearsActive = -5;

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Alpha(location, invalidYearsActive);
        });
    }

    @Test
    @DisplayName("Test null location throws exception")
    void testAlpha_NullLocation_ThrowsException() {
        // Arrange
        Location location = null;
        int yearsActive = 5;

        // Act & Assert
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new Alpha(location, yearsActive);
        });
    }


    @Test
    @DisplayName("Test Alpha rates initialization is valid")
    void testAlpha_RatesInitialization_ValidRates() {
        // Arrange
        Location location = Location.B;
        int yearsActive = 3;

        // Act
        Alpha alphaRecycling = new Alpha(location, yearsActive);
        List<Double> rates = alphaRecycling.getRates();

        // Assert
        assertNotNull(rates, "Rates should not be null.");
        assertFalse(rates.isEmpty(), "Rates should not be empty.");
        assertTrue(rates.stream().allMatch(rate -> rate > 0), "Rates should be greater than 0.");
    }

    @Test
    @DisplayName("Test Alpha constructor handles yearsActive (zero)")
    void testAlpha_YearsActive_ZeroBoundary() {
        // Arrange
        Location location = Location.C;
        int yearsActive = 0;

        // Act
        Alpha alphaRecycling = new Alpha(location, yearsActive);

        // Assert
        assertEquals(0, alphaRecycling.getYearsActive(), "Years active should handle the zero.");
    }

    @Test
    @DisplayName("Test Alpha constructor does not accept yearsActive greater than 100")
    void testAlpha_YearsActive_ExceedsMaxBoundary_ThrowsException() {
        // Arrange
        Location location = Location.B;
        int invalidYearsActive = 101; // Invalid yearsActive exceeding the maximum of 100

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Alpha(location, invalidYearsActive);
        });
    }

    @Test
    @DisplayName("Test Alpha handles invalid location")
    void testAlpha_Location_Invalid() {
        // Arrange
        String invalidLocation = "D"; // Invalid location outside A, B, C
        int yearsActive = 3;

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Alpha(Location.valueOf(invalidLocation), yearsActive);
        });

        assertEquals("Invalid location.", exception.getMessage());
        //assertEquals("No enum constant models.Location.D", exception.getMessage());
    }
}
