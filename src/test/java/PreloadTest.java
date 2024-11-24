import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class PreloadTest {

    @Test
    @DisplayName("Test viable centres with metallic waste")
    void testFindViableCentres_WithMetallicWaste_ReturnsAlphaAndBeta() {
        // Arrange
        Historic historic = new Historic(Location.A, 5000.0); // Contains metallic waste
        historic.setMetallic(1000.0); // Set metallic waste explicitly
        List<Recycling> centres = new ArrayList<>();
        centres.add(new Alpha(Location.A, 5)); // 1-hour travel
        centres.add(new Beta(Location.B, 3));  // 2-hour travel
        centres.add(new Gamma(Location.C, 12)); // 4-hour travel (should not be viable)

        // Act
        List<Recycling> viableCentres = Utils.findViableCentres(historic, centres);

        // Assert
        assertEquals(2, viableCentres.size(), "Only two centres should be viable.");
        assertTrue(viableCentres.stream().anyMatch(c -> c instanceof Alpha), "Alpha centre should be viable.");
        assertTrue(viableCentres.stream().anyMatch(c -> c instanceof Beta), "Beta centre should be viable.");
    }

    @Test
    @DisplayName("Test viable centres without metallic waste")
    void testFindViableCentres_WithoutMetallicWaste_ExcludesGamma() {
        // Arrange
        Historic historic = new Historic(Location.B, 3000.0); // No metallic waste
        historic.setMetallic(0.0); // Set metallic waste explicitly to zero
        List<Recycling> centres = new ArrayList<>();
        centres.add(new Alpha(Location.B, 8)); // 1-hour travel
        centres.add(new Beta(Location.A, 10)); // 2-hour travel
        centres.add(new Gamma(Location.C, 15)); // 3-hour travel (should be excluded due to no metallic waste)

        // Act
        List<Recycling> viableCentres = Utils.findViableCentres(historic, centres);

        // Assert
        assertEquals(2, viableCentres.size(), "Only two centres should be viable.");
        assertTrue(viableCentres.stream().anyMatch(c -> c instanceof Alpha), "Alpha centre should be viable.");
        assertTrue(viableCentres.stream().anyMatch(c -> c instanceof Beta), "Beta centre should be viable.");
        assertFalse(viableCentres.stream().anyMatch(c -> c instanceof Gamma), "Gamma centre should not be viable.");
    }

    @Test
    @DisplayName("Test viable centres with travel time boundary")
    void testFindViableCentres_TravelTimeBoundary_ReturnsBetaOnly() {
        // Arrange
        Historic historic = new Historic(Location.C, 2000.0);
        List<Recycling> centres = new ArrayList<>();
        centres.add(new Alpha(Location.A, 5)); // 4-hour travel (should not be viable)
        centres.add(new Beta(Location.B, 6));  // 3-hour travel (should be viable)

        // Act
        List<Recycling> viableCentres = Utils.findViableCentres(historic, centres);

        // Assert
        assertEquals(1, viableCentres.size(), "Only one centre should be viable.");
        assertTrue(viableCentres.stream().anyMatch(c -> c instanceof Beta), "Beta centre should be viable.");
        assertFalse(viableCentres.stream().anyMatch(c -> c instanceof Alpha), "Alpha centre should not be viable.");
    }

    @Test
    @DisplayName("Test viable centres when all are excluded due to travel time")
    void testFindViableCentres_ExcludedByTravelTime_ReturnsEmpty() {
        // Arrange
        Historic historic = new Historic(Location.A, 1500.0);
        List<Recycling> centres = new ArrayList<>();
        centres.add(new Alpha(Location.C, 7)); // 4-hour travel
        centres.add(new Beta(Location.C, 10)); // 4-hour travel

        // Act
        List<Recycling> viableCentres = Utils.findViableCentres(historic, centres);

        // Assert
        assertTrue(viableCentres.isEmpty(), "No centres should be viable due to travel time.");
    }

    @Test
    @DisplayName("Test viable centres with empty candidate list")
    void testFindViableCentres_EmptyCandidateList_ReturnsEmpty() {
        // Arrange
        Historic historic = new Historic(Location.B, 2500.0);
        List<Recycling> centres = new ArrayList<>(); // Empty list

        // Act
        List<Recycling> viableCentres = Utils.findViableCentres(historic, centres);

        // Assert
        assertTrue(viableCentres.isEmpty(), "No centres should be viable when candidate list is empty.");
    }

    @Test
    @DisplayName("Test viable centres with null candidate list")
    void testFindViableCentres_NullCandidates_ThrowsNullPointerException() {
        // Arrange
        Historic historic = new Historic(Location.A, 3000.0);

        // Act & Assert
        Exception exception = assertThrows(NullPointerException.class, () -> {
            Utils.findViableCentres(historic, null);
        });
        // Assert exception message (if applicable)
        assertEquals("Candidate centres cannot be null.", exception.getMessage());
    }

    @Test
    @DisplayName("Test viable centres with null historic site")
    void testFindViableCentres_NullHistoricSite_ThrowsNullPointerException() {
        // Arrange
        List<Recycling> centres = new ArrayList<>();
        centres.add(new Alpha(Location.A, 5));

        // Act & Assert
        Exception exception = assertThrows(NullPointerException.class, () -> {
            Utils.findViableCentres(null, centres);
        });

        // Assert exception message (if applicable)
        assertEquals("Historic site cannot be null.", exception.getMessage());
    }

    @Test
    @DisplayName("Test viable centres: Boundary travel time = 3 hours")
    void testFindViableCentres_TravelTimeBoundary_ReturnsTwoCentres() {
        // Arrange
        Historic historic = new Historic(Location.C, 3000.0);
        List<Recycling> centres = new ArrayList<>();
        centres.add(new Alpha(Location.A, 5)); // 4-hour travel (not viable)
        centres.add(new Beta(Location.B, 6));  // 3-hour travel (viable)
        centres.add(new Gamma(Location.C, 8)); // 1-hour travel (viable)

        // Act
        List<Recycling> viableCentres = Utils.findViableCentres(historic, centres);

        // Assert
        assertEquals(2, viableCentres.size(), "Two centres should be viable.");
        assertFalse(viableCentres.stream().anyMatch(c -> c instanceof Alpha), "Alpha centre should not be viable.");
        assertTrue(viableCentres.stream().anyMatch(c -> c instanceof Beta), "Beta centre should be viable.");
        assertTrue(viableCentres.stream().anyMatch(c -> c instanceof Gamma), "Gamma centre should be viable.");
    }

    @Test
    @DisplayName("Test viable centres: Complex scenario with metallic waste")
    void testFindViableCentres_ComplexWithMetallicWaste_ReturnsAlphaAndBeta() {
        // Arrange
        Historic historic = new Historic(Location.A, 5000.0); // 5000 cubic meters total waste
        historic.setMetallic(1000.0); // Metallic waste > 0

        // Add recycling centres
        List<Recycling> centres = new ArrayList<>();
        centres.add(new Alpha(Location.A, 5));  // 2-hour travel
        centres.add(new Beta(Location.B, 3));   // 3-hour travel
        centres.add(new Gamma(Location.C, 8)); // 4-hour travel (should not be viable)

        // Act
        List<Recycling> viableCentres = Utils.findViableCentres(historic, centres);

        // Assert
        assertEquals(2, viableCentres.size(), "Only centres within 3 hours should be viable.");
        assertTrue(viableCentres.stream().anyMatch(c -> c instanceof Alpha), "Alpha centre should be viable.");
        assertTrue(viableCentres.stream().anyMatch(c -> c instanceof Beta), "Beta centre should be viable.");
        assertFalse(viableCentres.stream().anyMatch(c -> c instanceof Gamma), "Gamma centre should not be viable.");

    }

}