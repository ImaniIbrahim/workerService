import org.junit.jupiter.api.Test;
import models.Historic;
import models.Location;
import models.Recycling;
import models.Alpha;
import models.Beta;
import models.Gamma;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScenarioConfigurationTest {
    // Positive Tests
    @Test
    @DisplayName("Constructor initialises fields correctly")
    void testConstructor_InitialisesFieldsCorrectly() {
        Historic historic = new Historic(Location.A, 200.0);
        List<Recycling> recyclingList = new ArrayList<>();
        recyclingList.add(new Alpha(Location.B, 5));
        ScenarioConfiguration config = new ScenarioConfiguration(historic, recyclingList);

        assertEquals(historic, config.getHistoric(), "Historic site should be initialised correctly.");
        assertEquals(recyclingList, config.getRecycling(), "Recycling list should be initialised correctly.");
    }

    @Test
    @DisplayName("Add recycling centre")
    void testAddRecycling_AddsCentre() {
        ScenarioConfiguration config = new ScenarioConfiguration();
        Recycling centre = new Beta(Location.A, 3);

        config.addRecycling(centre);

        assertEquals(1, config.getRecycling().size(), "Recycling list size should be 1 after adding a centre.");
        assertEquals(centre, config.getRecycling().get(0), "Added recycling centre should match the input.");
    }

    // Negative Tests
    @Test
    @DisplayName("Set historic to null")
    void testSetHistoric_NullValue() {
        ScenarioConfiguration config = new ScenarioConfiguration();

        assertThrows(NullPointerException.class, () -> config.setHistoric(null), "Setting Historic to null should throw NullPointerException.");
    }

    @Test
    @DisplayName("Add null recycling centre")
    void testAddRecycling_NullCentre() {
        ScenarioConfiguration config = new ScenarioConfiguration();

        assertThrows(NullPointerException.class, () -> config.addRecycling(null), "Adding null recycling centre should throw NullPointerException.");
    }


    @Test
    @DisplayName("Add maximum number of recycling centres")
    void testAddRecycling_MaximumNumber() {
        ScenarioConfiguration config = new ScenarioConfiguration();

        for (int i = 0; i < 1000; i++) { // Adjust this value as needed for testing capacity
            config.addRecycling(new Alpha(Location.A, 3));
        }

        assertEquals(1000, config.getRecycling().size(), "Recycling list size should match the number of added centres.");
    }

    // Equivalent Partitioning Tests
    @Test
    @DisplayName("Valid historic input")
    void testSetHistoric_ValidInput() {
        Historic historic = new Historic(Location.B, 300.0);
        ScenarioConfiguration config = new ScenarioConfiguration();

        config.setHistoric(historic);

        assertEquals(historic, config.getHistoric(), "Historic site should be set correctly.");
    }

    @Test
    @DisplayName("Valid recycling centre input")
    void testAddRecycling_ValidInput() {
        ScenarioConfiguration config = new ScenarioConfiguration();
        Recycling alpha = new Alpha(Location.A, 5);
        Recycling beta = new Beta(Location.B, 3);

        config.addRecycling(alpha);
        config.addRecycling(beta);

        assertEquals(2, config.getRecycling().size(), "Recycling list size should be 2.");
        assertTrue(config.getRecycling().contains(alpha), "Recycling list should contain the Alpha centre.");
        assertTrue(config.getRecycling().contains(beta), "Recycling list should contain the Beta centre.");
    }

    @Test
    @DisplayName("Null values in constructor")
    void testConstructor_NullValues() {
        assertThrows(NullPointerException.class, () -> new ScenarioConfiguration(null, null), "Constructor should throw NullPointerException for null inputs.");
    }

    @Test
    @DisplayName("Mixed valid and invalid entries in recycling list")
    void testAddRecycling_MixedEntries() {
        ScenarioConfiguration config = new ScenarioConfiguration();
        Recycling validCentre = new Alpha(Location.A, 5);

        config.addRecycling(validCentre);
        assertThrows(NullPointerException.class, () -> config.addRecycling(null), "Adding null should throw NullPointerException.");

        assertEquals(1, config.getRecycling().size(), "Only valid recycling centres should remain in the list.");
    }
    @Test
    @DisplayName("Test ScenarioConfiguration default constructor initializes recycling list")
    void testDefaultConstructor_InitializesRecyclingList() {
        // Act
        ScenarioConfiguration scenarioConfig = new ScenarioConfiguration();

        // Assert
        assertNotNull(scenarioConfig.getRecycling(), "Recycling list should be initialized.");
        assertTrue(scenarioConfig.getRecycling().isEmpty(), "Recycling list should be empty by default.");
    }

    @Test
    @DisplayName("Test ScenarioConfiguration parameterized constructor initializes fields correctly")
    void testParameterizedConstructor_InitializesFields() {
        // Arrange
        Historic historic = new Historic(Location.A, 100.0);
        List<Recycling> recyclingList = new ArrayList<>();
        recyclingList.add(new Alpha(Location.B, 5));

        // Act
        ScenarioConfiguration scenarioConfig = new ScenarioConfiguration(historic, recyclingList);

        // Assert
        assertEquals(historic, scenarioConfig.getHistoric(), "Historic field should be initialized correctly.");
        assertEquals(recyclingList, scenarioConfig.getRecycling(), "Recycling list should be initialized correctly.");
    }

    @Test
    @DisplayName("Test setHistoric updates the Historic field")
    void testSetHistoric_UpdatesHistoricField() {
        // Arrange
        ScenarioConfiguration scenarioConfig = new ScenarioConfiguration();
        Historic historic = new Historic(Location.B, 200.0);

        // Act
        scenarioConfig.setHistoric(historic);

        // Assert
        assertEquals(historic, scenarioConfig.getHistoric(), "Historic field should be updated.");
    }

    @Test
    @DisplayName("Test addRecycling adds a recycling centre to the list")
    void testAddRecycling_AddsRecyclingCentre() {
        // Arrange
        ScenarioConfiguration scenarioConfig = new ScenarioConfiguration();
        Recycling recycling = new Beta(Location.A, 10);

        // Act
        scenarioConfig.addRecycling(recycling);

        // Assert
        assertEquals(1, scenarioConfig.getRecycling().size(), "Recycling list should have one element.");
        assertTrue(scenarioConfig.getRecycling().contains(recycling), "Recycling list should contain the added recycling centre.");
    }

    @Test
    @DisplayName("Test getRecycling retrieves the recycling list")
    void testGetRecycling_RetrievesRecyclingList() {
        // Arrange
        List<Recycling> recyclingList = new ArrayList<>();
        recyclingList.add(new Gamma(Location.C, 7));
        ScenarioConfiguration scenarioConfig = new ScenarioConfiguration(null, recyclingList);

        // Act
        List<Recycling> result = scenarioConfig.getRecycling();

        // Assert
        assertEquals(recyclingList, result, "Recycling list should be retrieved correctly.");
    }



    @Test
    @DisplayName("Test ScenarioConfiguration handles adding null Recycling")
    void testAddRecycling_NullValue() {
        // Arrange
        ScenarioConfiguration scenarioConfig = new ScenarioConfiguration();

        // Act
        scenarioConfig.addRecycling(null);

        // Assert
        assertEquals(1, scenarioConfig.getRecycling().size(), "Recycling list should have one element even if null is added.");
        assertNull(scenarioConfig.getRecycling().get(0), "Recycling list should contain null as an element.");
    }

    @Test
    @DisplayName("Test ScenarioConfiguration handles empty recycling list")
    void testGetRecycling_EmptyList() {
        // Arrange
        ScenarioConfiguration scenarioConfig = new ScenarioConfiguration();

        // Act
        List<Recycling> result = scenarioConfig.getRecycling();

        // Assert
        assertNotNull(result, "Recycling list should not be null.");
        assertTrue(result.isEmpty(), "Recycling list should be empty.");
    }
}