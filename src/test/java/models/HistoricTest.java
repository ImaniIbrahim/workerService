package models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HistoricTest {

    // Positive Test Cases
    @Test
    @DisplayName("Test Historic constructor initializes remaining waste correctly")
    void testHistoric_constructor_InitializesRemainingWasteCorrectly() {
        // Arrange
        Location location = Location.A;
        double initialWaste = 2000.0;

        // Act
        Historic historic = new Historic(location, initialWaste);

        // Assert
        assertEquals(2000.0, historic.getRemainingWaste(), "Remaining waste should be initialized to the initial waste.");
    }

    // Threshold Test Cases
    @Test
    @DisplayName("Test Historic constructor splits waste exactly at metallic threshold")
    void testHistoric_constructor_SplitsWasteAtThreshold() {
        // Arrange
        Location location = Location.A;
        double initialWaste = 1250.0;

        // Act
        Historic historic = new Historic(location, initialWaste);

        // Assert
        assertEquals(625.0, historic.getPlasticGlass(), "Plastic/Glass waste should be 50% of total waste at the threshold.");
        assertEquals(625.0, historic.getPaper(), "Paper waste should be 50% of total waste.");
        assertEquals(0.0, historic.getMetallic(), "Metallic waste should be 0% at the threshold.");
    }

    @Test
    @DisplayName("Test Historic constructor splits waste below metallic threshold")
    void testHistoric_constructor_SplitsWasteBelowThreshold() {
        // Arrange
        Location location = Location.B;
        double initialWaste = 1249.0;

        // Act
        Historic historic = new Historic(location, initialWaste);

        // Assert
        assertEquals(624.5, historic.getPlasticGlass(), "Plastic/Glass waste should be 50% of total waste below the threshold.");
        assertEquals(624.5, historic.getPaper(), "Paper waste should be 50% of total waste below the threshold.");
        assertEquals(0.0, historic.getMetallic(), "Metallic waste should be 0% below the threshold.");
    }

    @Test
    @DisplayName("Test Historic constructor splits waste above metallic threshold")
    void testHistoric_constructor_SplitsWasteAboveThreshold() {
        // Arrange
        Location location = Location.C;
        double initialWaste = 1251.0;

        // Act
        Historic historic = new Historic(location, initialWaste);

        // Assert
        double delta = 0.0001;
        assertEquals(375.3, historic.getPlasticGlass(), delta, "Plastic/Glass waste should be 30% of total waste above the threshold.");
        assertEquals(625.5, historic.getPaper(), delta, "Paper waste should be 50% of total waste.");
        assertEquals(250.2, historic.getMetallic(), delta, "Metallic waste should be 20% of total waste above the threshold.");
    }

    // Negative Test Cases
    @Test
    @DisplayName("Test Historic constructor with negative waste throws exception")
    void testHistoric_constructor_NegativeWaste_ThrowsException() {
        // Arrange
        Location location = Location.A;
        double invalidWaste = -500.0;

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Historic(location, invalidWaste);
        });
        assertEquals("Waste cannot be negative.", exception.getMessage());
    }

    @Test
    @DisplayName("Test Historic constructor with null location throws exception")
    void testHistoric_constructor_NullLocation_ThrowsException() {
        // Arrange
        Location location = null;
        double initialWaste = 1000.0;

        // Act & Assert
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new Historic(location, initialWaste);
        });
        assertEquals("Location cannot be null.", exception.getMessage());
    }

    @Test
    @DisplayName("Test Historic constructor with invalid location throws exception")
    void testHistoric_constructor_InvalidLocation_ThrowsException() {
        // Arrange
        String invalidLocation = "D";
        double initialWaste = 1000.0;

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Historic(Location.valueOf(invalidLocation), initialWaste);
        });
        assertEquals("Invalid location provided.", exception.getMessage());
    }

    // Equivalent Partitioning Test Cases
    @Test
    @DisplayName("Test Historic set and get methods for remaining waste")
    void testHistoric_setGetRemainingWaste_ValidValue() {
        // Arrange
        Location location = Location.C;
        double initialWaste = 2000.0;
        Historic historic = new Historic(location, initialWaste);

        // Act
        historic.setRemainingWaste(1000.0);
        double remainingWaste = historic.getRemainingWaste();

        // Assert
        assertEquals(1000.0, remainingWaste, "Remaining waste should be updated correctly.");
    }

    @Test
    @DisplayName("Test Historic set and get methods for plastic/glass waste")
    void testHistoric_setGetPlasticGlass_ValidValue() {
        // Arrange
        Location location = Location.A;
        double initialWaste = 2000.0;
        Historic historic = new Historic(location, initialWaste);

        // Act
        historic.setPlasticGlass(600.0);
        double plasticGlass = historic.getPlasticGlass();

        // Assert
        assertEquals(600.0, plasticGlass, "Plastic/Glass waste should be updated correctly.");
    }

    @Test
    @DisplayName("Test Historic set and get methods for paper waste")
    void testHistoric_setGetPaper_ValidValue() {
        // Arrange
        Location location = Location.B;
        double initialWaste = 3000.0;
        Historic historic = new Historic(location, initialWaste);

        // Act
        historic.setPaper(1000.0);
        double paper = historic.getPaper();

        // Assert
        assertEquals(1000.0, paper, "Paper waste should be updated correctly.");
    }

    @Test
    @DisplayName("Test Historic set and get methods for metallic waste")
    void testHistoric_setGetMetallic_ValidValue() {
        // Arrange
        Location location = Location.C;
        double initialWaste = 4000.0;
        Historic historic = new Historic(location, initialWaste);

        // Act
        historic.setMetallic(500.0);
        double metallic = historic.getMetallic();

        // Assert
        assertEquals(500.0, metallic, "Metallic waste should be updated correctly.");
    }
}
