package models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GammaTest {

    // Positive Test Cases
    @Test
    @DisplayName("Test Gamma generation returns 'Gamma'")
    void testGamma_getGeneration_ReturnsGamma() {
        // Arrange
        Location location = Location.A;
        int yearsActive = 5;
        Gamma gammaRecycling = new Gamma(location, yearsActive);

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
        int yearsActive = 3;
        Gamma gammaRecycling = new Gamma(location, yearsActive);

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
        int yearsActive = 10;
        Gamma gammaRecycling = new Gamma(location, yearsActive);

        // Act
        int actualYearsActive = gammaRecycling.getYearsActive();

        // Assert
        assertEquals(10, actualYearsActive, "The yearsActive should return 10.");
    }

    @Test
    @DisplayName("Test Gamma location returns correct location")
    void testGamma_getLocation_ReturnsCorrectLocation() {
        // Arrange
        Location location = Location.A;
        int yearsActive = 7;
        Gamma gammaRecycling = new Gamma(location, yearsActive);

        // Act
        Location actualLocation = gammaRecycling.getLocation();

        // Assert
        assertEquals(Location.A, actualLocation, "The location should return 'A'.");
    }

    // Negative Test Cases
    @Test
    @DisplayName("Test negative yearsActive throws exception")
    void testGamma_InvalidYearsActive_ThrowsException() {
        // Arrange
        Location location = Location.C;
        int invalidYearsActive = -5;

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Gamma(location, invalidYearsActive);
        });

        assertEquals("Years active cannot be negative.", exception.getMessage());
    }

    @Test
    @DisplayName("Test null location throws exception")
    void testGamma_NullLocation_ThrowsException() {
        // Arrange
        Location location = null;
        int yearsActive = 5;

        // Act & Assert
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new Gamma(location, yearsActive);
        });

        assertEquals("Location cannot be null.", exception.getMessage());
    }

    // Boundary Test Cases
    @Test
    @DisplayName("Test Gamma constructor handles yearsActive (zero)")
    void testGamma_YearsActive_ZeroBoundary() {
        // Arrange
        Location location = Location.C;
        int yearsActive = 0;

        // Act
        Gamma gammaRecycling = new Gamma(location, yearsActive);

        // Assert
        assertEquals(0, gammaRecycling.getYearsActive(), "Years active should handle the zero boundary.");
    }

    @Test
    @DisplayName("Test Gamma constructor does not accept yearsActive greater than 100")
    void testGamma_YearsActive_ExceedsMaxBoundary_ThrowsException() {
        // Arrange
        Location location = Location.B;
        int invalidYearsActive = 101; // Invalid yearsActive exceeding the maximum of 100

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Gamma(location, invalidYearsActive);
        });

        assertEquals("Years active cannot exceed 100.", exception.getMessage());
    }

    // Equivalent Partitioning Test Cases
    @Test
    @DisplayName("Test Gamma handles valid location partitions")
    void testGamma_Location_ValidPartition() {
        // Arrange
        Location location = Location.A;
        int yearsActive = 3;

        // Act
        Gamma gammaRecycling = new Gamma(location, yearsActive);

        // Assert
        assertEquals(Location.A, gammaRecycling.getLocation(), "Gamma should handle valid location partition A.");
    }

    @Test
    @DisplayName("Test Gamma handles invalid location partition")
    void testGamma_Location_InvalidPartition() {
        // Arrange
        String invalidLocation = "D"; // Invalid location outside A, B, C
        int yearsActive = 3;

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Gamma(Location.valueOf(invalidLocation), yearsActive);
        });

        assertEquals("Invalid location provided.", exception.getMessage());
    }
}
