package models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecyclingTest {

    // Positive Test Cases
    @Test
    @DisplayName("Test Recycling getYearsActive returns correct years")
    void testRecycling_getYearsActive_ReturnsCorrectYears() {
        // Arrange
        Location location = Location.A;
        int yearsActive = 5;
        Recycling recycling = new Alpha(location, yearsActive); // Using Alpha as concrete implementation

        // Act
        int actualYearsActive = recycling.getYearsActive();

        // Assert
        assertEquals(5, actualYearsActive, "Years active should return 5.");
    }

    @Test
    @DisplayName("Test Recycling getLocation returns correct location")
    void testRecycling_getLocation_ReturnsCorrectLocation() {
        // Arrange
        Location location = Location.B;
        int yearsActive = 3;
        Recycling recycling = new Alpha(location, yearsActive);

        // Act
        Location actualLocation = recycling.getLocation();

        // Assert
        assertEquals(Location.B, actualLocation, "Location should return 'B'.");
    }

    @Test
    @DisplayName("Test Recycling getGeneration returns correct generation")
    void testRecycling_getGeneration_ReturnsCorrectGeneration() {
        // Arrange
        Location location = Location.C;
        int yearsActive = 10;
        Recycling recycling = new Alpha(location, yearsActive);

        // Act
        String generation = recycling.getGeneration();

        // Assert
        assertEquals("Alpha", generation, "Generation should return 'Alpha'.");
    }

    // Negative Test Cases
    @Test
    @DisplayName("Test Recycling with null location throws exception")
    void testRecycling_NullLocation_ThrowsException() {
        // Arrange
        Location location = null;
        int yearsActive = 5;

        // Act & Assert
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new Alpha(location, yearsActive);
        });

        assertEquals("Location cannot be null.", exception.getMessage());
    }

    @Test
    @DisplayName("Test Recycling with invalid yearsActive throws exception")
    void testRecycling_InvalidYearsActive_ThrowsException() {
        // Arrange
        Location location = Location.A;
        int invalidYearsActive = -1;

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Alpha(location, invalidYearsActive);
        });

        assertEquals("Years active cannot be negative.", exception.getMessage());
    }

    @Test
    @DisplayName("Test Recycling with invalid location throws exception")
    void testRecycling_InvalidLocation_ThrowsException() {
        // Arrange
        String invalidLocation = "D";
        int yearsActive = 5;

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Alpha(Location.valueOf(invalidLocation), yearsActive);
        });

        assertEquals("Invalid location provided.", exception.getMessage());
    }

    // Boundary Test Cases
    @Test
    @DisplayName("Test Recycling handles yearsActive at zero boundary")
    void testRecycling_YearsActive_ZeroBoundary() {
        // Arrange
        Location location = Location.B;
        int yearsActive = 0;
        Recycling recycling = new Alpha(location, yearsActive);

        // Act
        int actualYearsActive = recycling.getYearsActive();

        // Assert
        assertEquals(0, actualYearsActive, "Years active should handle zero boundary.");
    }

    @Test
    @DisplayName("Test Recycling does not accept yearsActive above maximum boundary")
    void testRecycling_YearsActive_ExceedsMaxBoundary() {
        // Arrange
        Location location = Location.C;
        int invalidYearsActive = 101;

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Alpha(location, invalidYearsActive);
        });

        assertEquals("Years active cannot exceed 100.", exception.getMessage());
    }

    // Equivalent Partitioning Test Cases
    @Test
    @DisplayName("Test Recycling handles valid location partition")
    void testRecycling_Location_ValidPartition() {
        // Arrange
        Location location = Location.A;
        int yearsActive = 5;
        Recycling recycling = new Alpha(location, yearsActive);

        // Act
        Location actualLocation = recycling.getLocation();

        // Assert
        assertEquals(Location.A, actualLocation, "Recycling should handle valid location partition 'A'.");
    }

    @Test
    @DisplayName("Test Recycling handles rates correctly")
    void testRecycling_Rates_ReturnsValidRates() {
        // Arrange
        Location location = Location.B;
        int yearsActive = 3;
        Recycling recycling = new Alpha(location, yearsActive);

        // Act
        List<Double> rates = recycling.getRates();

        // Assert
        assertNotNull(rates, "Rates should not be null.");
        assertFalse(rates.isEmpty(), "Rates should not be empty.");
        assertTrue(rates.stream().allMatch(rate -> rate > 0), "Rates should all be greater than 0.");
    }
}
