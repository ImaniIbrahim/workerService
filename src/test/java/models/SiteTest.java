package models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SiteTest {

    // Helper Class to Create Testable Site Instance
    static class TestSite extends Site {
        TestSite(Location location) {
            super(location);
        }
    }

    // Positive Test Cases
    @Test
    @DisplayName("Test Site constructor sets location correctly")
    void testSite_constructor_SetsLocationCorrectly() {
        // Arrange
        Location location = Location.A;

        // Act
        Site site = new TestSite(location);

        // Assert
        assertEquals(Location.A, site.getLocation(), "Location should be set to A.");
    }

    @Test
    @DisplayName("Test getLocation returns correct location")
    void testSite_getLocation_ReturnsCorrectLocation() {
        // Arrange
        Location location = Location.B;
        Site site = new TestSite(location);

        // Act
        Location actualLocation = site.getLocation();

        // Assert
        assertEquals(Location.B, actualLocation, "getLocation should return B.");
    }

    // Negative Test Cases
    @Test
    @DisplayName("Test Site constructor with null location throws exception")
    void testSite_constructor_NullLocation_ThrowsException() {
        // Arrange
        Location location = null;

        // Act & Assert
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new TestSite(location);
        });

        assertEquals("Location cannot be null.", exception.getMessage());
    }

    @Test
    @DisplayName("Test Site handles valid location partitions")
    void testSite_Location_ValidPartition() {
        // Arrange
        Location location = Location.C;

        // Act
        Site site = new TestSite(location);

        // Assert
        assertEquals(Location.C, site.getLocation(), "Location should be C.");
    }

    @Test
    @DisplayName("Test Site with invalid location throws exception")
    void testSite_Location_InvalidPartition() {
        // Arrange
        String invalidLocation = "D";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new TestSite(Location.valueOf(invalidLocation));
        });

        assertEquals("Invalid location.", exception.getMessage());
        //assertEquals("No enum constant models.Location.D", exception.getMessage());
    }
}
