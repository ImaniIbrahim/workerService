package models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    // Positive Test Cases
    @Test
    @DisplayName("Test travel time between same locations")
    void testLocation_travelTime_SameLocation_ReturnsZero() {
        // Arrange
        Location location = Location.A;

        // Act
        double travelTime = location.travelTime(location);

        // Assert
        assertEquals(0.0, travelTime, "Travel time between the same locations should be 0.");
    }

    @Test
    @DisplayName("Test travel time between A and B")
    void testLocation_travelTime_BetweenAandB_ReturnsTwo() {
        // Arrange
        Location from = Location.A;
        Location to = Location.B;

        // Act
        double travelTime = from.travelTime(to);

        // Assert
        assertEquals(2.0, travelTime, "Travel time between A and B should be 2.0.");
    }

    @Test
    @DisplayName("Test travel time between A and C")
    void testLocation_travelTime_BetweenAandC_ReturnsFour() {
        // Arrange
        Location from = Location.A;
        Location to = Location.C;

        // Act
        double travelTime = from.travelTime(to);

        // Assert
        assertEquals(4.0, travelTime, "Travel time between A and C should be 4.0.");
    }

    @Test
    @DisplayName("Test travel time between B and C")
    void testLocation_travelTime_BetweenBandC_ReturnsThree() {
        // Arrange
        Location from = Location.B;
        Location to = Location.C;

        // Act
        double travelTime = from.travelTime(to);

        // Assert
        assertEquals(3.0, travelTime, "Travel time between B and C should be 3.0.");
    }

    // Negative Test Cases
    @Test
    @DisplayName("Test travel time with null destination throws exception")
    void testLocation_travelTime_NullDestination_ThrowsException() {
        // Arrange
        Location from = Location.A;
        Location to = null;

        // Act & Assert
        Exception exception = assertThrows(NullPointerException.class, () -> from.travelTime(to));
    }

    @Test
    @DisplayName("Test travel time with invalid location throws exception")
    void testLocation_travelTime_InvalidLocation_ThrowsException() {
        // Arrange
        String invalidLocation = "D";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Location.valueOf(invalidLocation));
        assertEquals("No enum constant models.Location.D", exception.getMessage());
    }

    // Boundary Test Cases
    @Test
    @DisplayName("Test travel time between A and B handles boundary condition")
    void testLocation_travelTime_BetweenAandB_HandlesBoundary() {
        // Arrange
        Location from = Location.A;
        Location to = Location.B;

        // Act
        double travelTime = from.travelTime(to);

        // Assert
        assertEquals(2.0, travelTime, "Travel time between A and B should handle boundary correctly.");
    }

    @Test
    @DisplayName("Test travel time between B and C handles boundary condition")
    void testLocation_travelTime_BetweenBandC_HandlesBoundary() {
        // Arrange
        Location from = Location.B;
        Location to = Location.C;

        // Act
        double travelTime = from.travelTime(to);

        // Assert
        assertEquals(3.0, travelTime, "Travel time between B and C should handle boundary correctly.");
    }

    // Equivalent Partitioning Test Cases
    @Test
    @DisplayName("Test travel time handles valid input partition A to B")
    void testLocation_travelTime_ValidPartition_AtoB() {
        // Arrange
        Location from = Location.A;
        Location to = Location.B;

        // Act
        double travelTime = from.travelTime(to);

        // Assert
        assertEquals(2.0, travelTime, "Travel time between A and B should be valid.");
    }

    @Test
    @DisplayName("Test travel time handles valid input partition B to C")
    void testLocation_travelTime_ValidPartition_BtoC() {
        // Arrange
        Location from = Location.B;
        Location to = Location.C;

        // Act
        double travelTime = from.travelTime(to);

        // Assert
        assertEquals(3.0, travelTime, "Travel time between B and C should be valid.");
    }

    @Test
    @DisplayName("Test travel time handles valid input partition A to C")
    void testLocation_travelTime_ValidPartition_AtoC() {
        // Arrange
        Location from = Location.A;
        Location to = Location.C;

        // Act
        double travelTime = from.travelTime(to);

        // Assert
        assertEquals(4.0, travelTime, "Travel time between A and C should be valid.");
    }

    @Test
    @DisplayName("Test symmetry between A to B and B to A")
    void testLocation_travelTime_Symmetric_BetweenAandB() {
        // Arrange
        Location fromA = Location.A;
        Location toB = Location.B;

        Location fromB = Location.B;
        Location toA = Location.A;

        // Act
        double travelTimeAB = fromA.travelTime(toB);
        double travelTimeBA = fromB.travelTime(toA);

        // Assert
        assertEquals(travelTimeAB, travelTimeBA, "Travel time between A and B should be symmetric and equal to 2.");
        assertEquals(2.0, travelTimeAB, "Travel time from A to B should be 2.0.");
        assertEquals(2.0, travelTimeBA, "Travel time from B to A should be 2.0.");
    }


}
