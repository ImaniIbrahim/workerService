package models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class TransportTest {

    @Test
    @DisplayName("Test Transport constructor initializes start and end locations correctly")
    void testTransport_Constructor_InitializesStartAndEnd() throws NoSuchFieldException, IllegalAccessException {
        // Arrange
        Location start = Location.A;
        Location end = Location.B;
        Transport transport = new Transport(start, end);

        // Act
        Field startField = Transport.class.getDeclaredField("start");
        Field endField = Transport.class.getDeclaredField("end");

        // Allow access to private fields
        startField.setAccessible(true);
        endField.setAccessible(true);

        Location actualStart = (Location) startField.get(transport);
        Location actualEnd = (Location) endField.get(transport);

        // Assert
        assertEquals(start, actualStart, "Start location should be initialized correctly.");
        assertEquals(end, actualEnd, "End location should be initialized correctly.");
    }

    @Test
    @DisplayName("Test Transport calculates correct travel time between A and B")
    void testTransport_CalculatesTravelTime_BetweenAandB() {
        // Arrange
        Transport transport = new Transport(Location.A, Location.B);

        // Act
        double travelTime = transport.getTravelTime();

        // Assert
        assertEquals(2.0, travelTime, "Travel time between A and B should be 2.0.");
    }

    @Test
    @DisplayName("Test Transport calculates correct travel time within the same location")
    void testTransport_CalculatesTravelTime_SameLocation() {
        // Arrange
        Transport transport = new Transport(Location.A, Location.A);

        // Act
        double travelTime = transport.getTravelTime();

        // Assert
        assertEquals(1.0, travelTime, "Travel time within the same location should be 1.0.");
    }

    @Test
    @DisplayName("Test Transport calculates correct travel time between B and C")
    void testTransport_CalculatesTravelTime_BetweenBandC() {
        // Arrange
        Transport transport = new Transport(Location.B, Location.C);

        // Act
        double travelTime = transport.getTravelTime();

        // Assert
        assertEquals(3.0, travelTime, "Travel time between B and C should be 3.0.");
    }

    @Test
    @DisplayName("Test Transport calculates correct travel time between A and C")
    void testTransport_CalculatesTravelTime_BetweenAandC() {
        // Arrange
        Transport transport = new Transport(Location.A, Location.C);

        // Act
        double travelTime = transport.getTravelTime();

        // Assert
        assertEquals(4.0, travelTime, "Travel time between A and C should be 4.0.");
    }

    @Test
    @DisplayName("Test Transport calculates total waste")
    void testTransport_CalculatesTotalWaste() {
        // Arrange
        Transport transport = new Transport(Location.A, Location.B);
        transport.setPaperWaste(100.0);
        transport.setPlasticGlassWaste(200.0);
        transport.setMetallicWaste(50.0);

        // Act
        double totalWaste = transport.getTotalWaste();

        // Assert
        assertEquals(350.0, totalWaste, "Total waste should be the sum of all waste types.");
    }

    @Test
    @DisplayName("Test Transport handles zero waste")
    void testTransport_CalculatesZeroWaste() {
        // Arrange
        Transport transport = new Transport(Location.A, Location.B);

        // Act
        double totalWaste = transport.getTotalWaste();

        // Assert
        assertEquals(0.0, totalWaste, "Total waste should be 0.0 when no waste is added.");
    }

    @Test
    @DisplayName("Test Transport with negative waste throws exception")
    void testTransport_NegativeWaste_ThrowsException() {
        // Arrange
        Transport transport = new Transport(Location.A, Location.B);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transport.setMetallicWaste(-10.0);
        });
        assertEquals("Waste cannot be negative.", exception.getMessage());
    }

    @Test
    @DisplayName("Test Transport with invalid location throws exception")
    void testTransport_InvalidLocation_ThrowsException() {
        // Arrange
        String invalidLocation = "D";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Location.valueOf(invalidLocation));
        assertEquals("No enum constant models.Location.D", exception.getMessage());
    }

    @Test
    @DisplayName("Test Transport travel time is symmetric between A and B")
    void testTransport_TravelTime_Symmetric_BetweenAandB() {
        // Arrange
        Transport transportAB = new Transport(Location.A, Location.B);
        Transport transportBA = new Transport(Location.B, Location.A);

        // Act
        double travelTimeAB = transportAB.getTravelTime();
        double travelTimeBA = transportBA.getTravelTime();

        // Assert
        assertEquals(travelTimeAB, travelTimeBA, "Travel time between A and B should be symmetric.");
        assertEquals(2.0, travelTimeAB, "Travel time from A to B should be 2.0.");
        assertEquals(2.0, travelTimeBA, "Travel time from B to A should be 2.0.");
    }

    @Test
    @DisplayName("Test Transport handles edge case for total waste calculation")
    void testTransport_EdgeCase_TotalWaste() {
        // Arrange
        Transport transport = new Transport(Location.A, Location.B);
        transport.setPaperWaste(Double.MAX_VALUE);
        transport.setPlasticGlassWaste(Double.MIN_VALUE);
        transport.setMetallicWaste(0.0);

        // Act
        double totalWaste = transport.getTotalWaste();

        // Assert
        assertTrue(totalWaste > 0, "Total waste should be valid for edge case values.");
    }
}
