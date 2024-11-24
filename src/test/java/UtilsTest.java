import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {


    // Unit tests for findViableCentres business logic [Start]
    //===============================================================================

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
// Unit tests for findViableCentres business logic [End]
    // =======================================================
// Unit tests for findOptimalCentre business logic [Start]


    @Test
    @DisplayName("Test optimal centre is nearest")
    void testFindOptimalCentre_Nearest_ReturnsNearestCentre() {
        // Arrange
        Historic historic = new Historic(Location.A, 5000.0);
        List<Recycling> centres = new ArrayList<>();
        centres.add(new Alpha(Location.A, 10)); // 0 hours
        centres.add(new Beta(Location.B, 8)); // 2 hours
        centres.add(new Gamma(Location.C, 12)); // 4 hours

        // Act
        Recycling optimalCentre = Utils.findOptimalCentre(historic, centres);

        // Assert
        assertTrue(optimalCentre instanceof Alpha, "The nearest centre should be selected as optimal.");
        assertEquals(Location.A, optimalCentre.getLocation(), "Optimal centre should be in location A.");
    }

    @Test
    @DisplayName("Test optimal centre with same distance but higher generation")
    void testFindOptimalCentre_SameDistanceHigherGeneration_ReturnsGammaCentre() {
        // Arrange
        Historic historic = new Historic(Location.A, 5000.0);
        List<Recycling> centres = new ArrayList<>();
        centres.add(new Alpha(Location.B, 10)); // 2 hours
        centres.add(new Gamma(Location.B, 12)); // 2 hours but higher generation

        // Act
        Recycling optimalCentre = Utils.findOptimalCentre(historic, centres);

        // Assert
        assertTrue(optimalCentre instanceof Gamma, "Centre with higher generation should be selected as optimal.");
    }

    @Test
    @DisplayName("Test optimal centre with same distance and generation but fewer years active")
    void testFindOptimalCentre_SameDistanceSameGenerationFewerYears_ReturnsFewerYearsCentre() {
        // Arrange
        Historic historic = new Historic(Location.A, 5000.0);
        List<Recycling> centres = new ArrayList<>();
        centres.add(new Beta(Location.B, 10)); // 2 hours, 10 years active
        centres.add(new Beta(Location.B, 5)); // 2 hours, 5 years active

        // Act
        Recycling optimalCentre = Utils.findOptimalCentre(historic, centres);

        // Assert
        assertEquals(5, optimalCentre.getYearsActive(), "Centre with fewer years active should be selected as optimal.");
    }

    @Test
    @DisplayName("Test optimal centre with multiple highest priority centres")
    void testFindOptimalCentre_MultipleHighestPriorityCentres_ReturnsOneCentre() {
        // Arrange
        Historic historic = new Historic(Location.A, 5000.0);
        List<Recycling> centres = new ArrayList<>();
        centres.add(new Alpha(Location.A, 5)); // Same priority as below
        centres.add(new Alpha(Location.A, 5)); // Same priority

        // Act
        Recycling optimalCentre = Utils.findOptimalCentre(historic, centres);

        // Assert
        assertNotNull(optimalCentre, "An optimal centre should be chosen even with multiple highest priority centres.");
        assertEquals(Location.A, optimalCentre.getLocation(), "Optimal centre should be in location A.");
    }

    @Test
    @DisplayName("Test findOptimalCentre throws exception with null candidate list")
    void testFindOptimalCentre_NullCandidateList_ThrowsException() {
        // Arrange
        Historic historic = new Historic(Location.A, 5000.0);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> Utils.findOptimalCentre(historic, null),
                "Null candidate list should throw NullPointerException.");
    }

    @Test
    @DisplayName("Test findOptimalCentre throws exception with null historic site")
    void testFindOptimalCentre_NullHistoricSite_ThrowsException() {
        // Arrange
        List<Recycling> centres = new ArrayList<>();
        centres.add(new Alpha(Location.A, 5));

        // Act & Assert
        assertThrows(NullPointerException.class, () -> Utils.findOptimalCentre(null, centres),
                "Null historic site should throw NullPointerException.");
    }

    @Test
    @DisplayName("Test optimal centre when no viable centres are available")
    void testFindOptimalCentre_NoViableCentres_ReturnsNull() {
        // Arrange
        Historic historic = new Historic(Location.A, 5000.0);
        List<Recycling> centres = new ArrayList<>();
        centres.add(new Gamma(Location.C, 15)); // Travel time exceeds 3 hours

        // Act
        Recycling optimalCentre = Utils.findOptimalCentre(historic, centres);

        // Assert
        assertNull(optimalCentre, "Optimal centre should be null when no viable centres are available.");
    }

    @Test
    @DisplayName("Test optimal centre with metallic waste and viable Gamma centre")
    void testFindOptimalCentre_WithMetallicWaste_ReturnsGammaCentre() {
        // Arrange
        Historic historic = new Historic(Location.A, 5000.0);
        historic.setMetallic(1000.0); // Explicitly set metallic waste
        List<Recycling> centres = new ArrayList<>();
        centres.add(new Gamma(Location.B, 8)); // Gamma centre is viable

        // Act
        Recycling optimalCentre = Utils.findOptimalCentre(historic, centres);

        // Assert
        assertTrue(optimalCentre instanceof Gamma, "Gamma centre should be selected as optimal with metallic waste.");
    }

    @Test
    @DisplayName("Test optimal centre without metallic waste excludes Gamma")
    void testFindOptimalCentre_WithoutMetallicWaste_ReturnsBetaCentre() {
        // Arrange
        Historic historic = new Historic(Location.A, 5000.0);
        historic.setMetallic(0.0); // No metallic waste
        List<Recycling> centres = new ArrayList<>();
        centres.add(new Gamma(Location.B, 8)); // Gamma should be excluded
        centres.add(new Beta(Location.C, 12)); // Beta should be optimal

        // Act
        Recycling optimalCentre = Utils.findOptimalCentre(historic, centres);

        // Assert
        assertTrue(optimalCentre instanceof Beta, "Beta centre should be selected as optimal without metallic waste.");
    }


    // Unit tests for findOptimalCentre business logic [End]
    // =======================================================
    // Unit tests for findNearestCentres business logic [Start]

    @Test
    @DisplayName("Find nearest centre: Single candidate")
    void testFindNearestCentres_SingleCandidate_ReturnsSingleCentre() {
        // Arrange
        Historic historic = new Historic(Location.A, 2000.0);
        List<Recycling> candidateCentres = List.of(new Alpha(Location.B, 5));

        // Act
        List<Recycling> nearestCentres = Utils.findNearestCentres(historic, candidateCentres);

        // Assert
        assertEquals(1, nearestCentres.size(), "Only one centre should be returned.");
        assertEquals(candidateCentres.get(0), nearestCentres.get(0), "The single candidate should be returned.");
    }

    @Test
    @DisplayName("Find nearest centre: Multiple candidates, one nearest")
    void testFindNearestCentres_MultipleCandidates_OneNearest() {
        // Arrange
        Historic historic = new Historic(Location.A, 2000.0);
        List<Recycling> candidateCentres = List.of(
                new Alpha(Location.B, 5), // 2 hours
                new Beta(Location.C, 8)   // 4 hours
        );

        // Act
        List<Recycling> nearestCentres = Utils.findNearestCentres(historic, candidateCentres);

        // Assert
        assertEquals(1, nearestCentres.size(), "Only one nearest centre should be returned.");
        assertTrue(nearestCentres.get(0) instanceof Alpha, "The nearest centre should be Alpha.");
    }

    @Test
    @DisplayName("Find nearest centre: Multiple candidates with same travel time")
    void testFindNearestCentres_MultipleCandidates_SameTravelTime() {
        // Arrange
        Historic historic = new Historic(Location.A, 2000.0);
        List<Recycling> candidateCentres = List.of(
                new Alpha(Location.B, 5), // 2 hours
                new Beta(Location.B, 3)   // 2 hours
        );

        // Act
        List<Recycling> nearestCentres = Utils.findNearestCentres(historic, candidateCentres);

        // Assert
        assertEquals(2, nearestCentres.size(), "Both centres with the same travel time should be returned.");
        assertTrue(nearestCentres.stream().anyMatch(c -> c instanceof Alpha), "Alpha should be in the result.");
        assertTrue(nearestCentres.stream().anyMatch(c -> c instanceof Beta), "Beta should be in the result.");
    }

    @Test
    @DisplayName("Find nearest centre: Empty candidate list")
    void testFindNearestCentres_EmptyCandidates_ReturnsEmptyList() {
        // Arrange
        Historic historic = new Historic(Location.A, 2500.0);
        List<Recycling> candidateCentres = Collections.emptyList();

        // Act
        List<Recycling> nearestCentres = Utils.findNearestCentres(historic, candidateCentres);

        // Assert
        assertTrue(nearestCentres.isEmpty(), "No centres should be returned for an empty candidate list.");
    }

    @Test
    @DisplayName("Find nearest centre: Null candidate list throws exception")
    void testFindNearestCentres_NullCandidates_ThrowsException() {
        // Arrange
        Historic historic = new Historic(Location.A, 3000.0);

        // Act & Assert
        Exception exception = assertThrows(NullPointerException.class, () -> Utils.findNearestCentres(historic, null));
        assertEquals("Candidate centres cannot be null.", exception.getMessage());
    }

    @Test
    @DisplayName("Find nearest centre: Null historic site throws exception")
    void testFindNearestCentres_NullHistoric_ThrowsException() {
        // Arrange
        List<Recycling> candidateCentres = List.of(new Alpha(Location.B, 5));

        // Act & Assert
        Exception exception = assertThrows(NullPointerException.class, () -> Utils.findNearestCentres(null, candidateCentres));
        assertEquals("Historic site cannot be null.", exception.getMessage());
    }

    @Test
    @DisplayName("Find nearest centre: All candidates beyond travel limit")
    void testFindNearestCentres_AllBeyondLimit_ReturnsEmptyList() {
        // Arrange
        Historic historic = new Historic(Location.A, 2500.0);
        List<Recycling> candidateCentres = List.of(
                new Alpha(Location.C, 7), // 4-hour travel
                new Beta(Location.C, 10)  // 4-hour travel
        );

        // Act
        List<Recycling> nearestCentres = Utils.findNearestCentres(historic, candidateCentres);

        // Assert
        assertTrue(nearestCentres.isEmpty(), "No centres should be returned when all candidates exceed the travel time.");
    }

    @Test
    @DisplayName("Find nearest centre: Boundary travel time = Minimum")
    void testFindNearestCentres_BoundaryTravelTime_ReturnsAlphaCentre() {
        // Arrange
        Historic historic = new Historic(Location.B, 3000.0);
        List<Recycling> candidateCentres = List.of(
                new Alpha(Location.B, 8),  // 1-hour travel
                new Beta(Location.C, 6),  // 3-hour travel
                new Gamma(Location.A, 7)  // 2-hour travel
        );

        // Act
        List<Recycling> nearestCentres = Utils.findNearestCentres(historic, candidateCentres);

        // Assert
        assertEquals(1, nearestCentres.size(), "Only one centre should match the minimum travel time.");
        assertTrue(nearestCentres.stream().anyMatch(c -> c instanceof Alpha), "Alpha should be the nearest centre.");
    }

    @Test
    @DisplayName("Find nearest centre: Centres in the same location as historic site")
    void testFindNearestCentres_CentresInSameLocation() {
        // Arrange
        Historic historic = new Historic(Location.A, 2000.0);
        List<Recycling> candidateCentres = List.of(
                new Alpha(Location.A, 5),
                new Beta(Location.A, 3)
        );

        // Act
        List<Recycling> nearestCentres = Utils.findNearestCentres(historic, candidateCentres);

        // Assert
        assertEquals(2, nearestCentres.size(), "All centres in the same location should be returned.");
    }

    // Unit tests for findNearestCentres business logic [End]
    // =======================================================
    // Unit tests for findNearestCentres business logic [Start]



//    @BeforeEach
//    void setUp() {
//    }
//
//    @Test
//    void findViableCentres() {
//    }
//
//    @Test
//    void findOptimalCentre() {
//    }
//
//    @Test
//    void findNearestCentres() {
//    }
//
//    @Test
//    void findHighestGenerations() {
//    }
//
//    @Test
//    void compareGenerations() {
//    }
//
//    @Test
//    void findLeastYearsActive() {
//    }
//
//    @Test
//    void calculateTravelDuration() {
//    }
//
//    @Test
//    void calculateProcessDuration() {
//    }
}