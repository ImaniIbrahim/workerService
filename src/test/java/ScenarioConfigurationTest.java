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
    @DisplayName("Constructor initializes fields correctly")
    void testScenarioConfiguration_InitialisesFields_ReturnedHistoricAndRecyclingData() {
        // Arrange
        Historic historic = new Historic(Location.A, 200.0);
        List<Recycling> recyclingList = new ArrayList<>();
        recyclingList.add(new Alpha(Location.B, 5));

        // Act
        ScenarioConfiguration config = new ScenarioConfiguration(historic, recyclingList);

        // Assert
        assertEquals(historic, config.getHistoric(), "Historic site should be initialized correctly.");
        assertEquals(recyclingList, config.getRecycling(), "Recycling list should be initialized correctly.");
    }

    @Test
    @DisplayName("Add recycling centre successfully")
    void testAddRecycling_ValidCentre_AddedToList() {
        // Arrange
        ScenarioConfiguration config = new ScenarioConfiguration();
        Recycling centre = new Beta(Location.A, 3);

        // Act
        config.addRecycling(centre);

        // Assert
        assertEquals(1, config.getRecycling().size(), "Recycling list size should be 1 after adding a centre.");
        assertEquals(centre, config.getRecycling().get(0), "Added recycling centre should match the input.");
    }

    // Negative Tests
    @Test
    @DisplayName("Set historic to null throws exception")
    void testSetHistoric_NullValue_ThrowsException() {
        // Arrange
        ScenarioConfiguration config = new ScenarioConfiguration();

        // Act & Assert
        assertThrows(NullPointerException.class, () -> config.setHistoric(null),
                "Setting Historic to null should throw NullPointerException.");
    }

    @Test
    @DisplayName("Add null recycling centre throws exception")
    void testAddRecycling_NullCentre_ThrowsException() {
        // Arrange
        ScenarioConfiguration config = new ScenarioConfiguration();

        // Act & Assert
        assertThrows(NullPointerException.class, () -> config.addRecycling(null),
                "Adding null recycling centre should throw NullPointerException.");
    }


    // Equivalent Partitioning Tests
    @Test
    @DisplayName("Set valid historic input")
    void testSetHistoric_ValidInput_SuccessfullyUpdated() {
        // Arrange
        Historic historic = new Historic(Location.B, 300.0);
        ScenarioConfiguration config = new ScenarioConfiguration();

        // Act
        config.setHistoric(historic);

        // Assert
        assertEquals(historic, config.getHistoric(), "Historic site should be set correctly.");
    }

    @Test
    @DisplayName("Add valid recycling centres")
    void testAddRecycling_ValidCentres_AllAdded() {
        // Arrange
        ScenarioConfiguration config = new ScenarioConfiguration();
        Recycling alpha = new Alpha(Location.A, 5);
        Recycling beta = new Beta(Location.B, 3);

        // Act
        config.addRecycling(alpha);
        config.addRecycling(beta);

        // Assert
        assertEquals(2, config.getRecycling().size(), "Recycling list size should be 2.");
        assertTrue(config.getRecycling().contains(alpha), "Recycling list should contain the Alpha centre.");
        assertTrue(config.getRecycling().contains(beta), "Recycling list should contain the Beta centre.");
    }

    @Test
    @DisplayName("Constructor with null values throws exception")
    void testScenarioConfiguration_NullValues_ThrowsException() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> new ScenarioConfiguration(null, null),
                "Constructor should throw NullPointerException for null inputs.");
    }


    @Test
    @DisplayName("Default constructor initialises recycling list")
    void testScenarioConfiguration_DefaultConstructor_RecyclingListInitialised() {
        // Act
        ScenarioConfiguration scenarioConfig = new ScenarioConfiguration();

        // Assert
        assertNotNull(scenarioConfig.getRecycling(), "Recycling list should be initialized.");
        assertTrue(scenarioConfig.getRecycling().isEmpty(), "Recycling list should be empty by default.");
    }

    @Test
    @DisplayName("Parameterised constructor initializes fields")
    void testScenarioConfiguration_ParameterizedConstructor_FieldsInitializedCorrectly() {
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
    @DisplayName("Update Historic field")
    void testSetHistoric_ValidHistoricFieldUpdated() {
        // Arrange
        ScenarioConfiguration scenarioConfig = new ScenarioConfiguration();
        Historic historic = new Historic(Location.B, 200.0);

        // Act
        scenarioConfig.setHistoric(historic);

        // Assert
        assertEquals(historic, scenarioConfig.getHistoric(), "Historic field should be updated.");
    }

    @Test
    @DisplayName("Add a recycling centre")
    void testAddRecycling_AddsValidRecyclingCentre() {
        // Arrange
        ScenarioConfiguration scenarioConfig = new ScenarioConfiguration();
        Recycling recycling = new Beta(Location.A, 10);

        // Act
        scenarioConfig.addRecycling(recycling);

        // Assert
        assertEquals(1, scenarioConfig.getRecycling().size(), "Recycling list should have one element.");
        assertTrue(scenarioConfig.getRecycling().contains(recycling),
                "Recycling list should contain the added recycling centre.");
    }

    @Test
    @DisplayName("Retrieve recycling list")
    void testGetRecycling_ReturnsRecyclingList() {
        // Arrange
        List<Recycling> recyclingList = new ArrayList<>();
        recyclingList.add(new Gamma(Location.C, 7));
        ScenarioConfiguration scenarioConfig = new ScenarioConfiguration(null, recyclingList);

        // Act
        List<Recycling> result = scenarioConfig.getRecycling();

        // Assert
        assertEquals(recyclingList, result, "Recycling list should be retrieved correctly.");
    }
}