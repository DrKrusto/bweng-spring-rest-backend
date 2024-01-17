package at.technikum.springrestbackend.dto;

import at.technikum.springrestbackend.dto.availability.CreateSpecificAvailabilityRequest;
import at.technikum.springrestbackend.model.SpecificAvailability;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class SpecificAvailabilityRequestTest {

    @Test
    void constructor_SetsFieldsCorrectly() {
        // Arrange
        UUID lawyerId = UUID.randomUUID();
        String date = "2022-01-01";
        String startTime = "09:00";
        String endTime = "17:00";

        // Act
        CreateSpecificAvailabilityRequest request = new CreateSpecificAvailabilityRequest(
                lawyerId, date, startTime, endTime);

        // Assert
        assertEquals(lawyerId, request.getLawyerId());
        assertEquals(date, request.getDate());
        assertEquals(startTime, request.getStartTime());
        assertEquals(endTime, request.getEndTime());
    }

    @Test
    void toSpecificAvailability_ConvertsCorrectly() {
        // Arrange
        UUID lawyerId = UUID.randomUUID();
        String date = "2022-01-01";
        String startTime = "09:00";
        String endTime = "17:00";

        CreateSpecificAvailabilityRequest request = new CreateSpecificAvailabilityRequest(
                lawyerId, date, startTime, endTime);

        // Act
        SpecificAvailability specificAvailability = request.toSpecificAvailability();

        // Assert
        assertNotNull(specificAvailability.getId());
        assertEquals(java.time.LocalDate.parse(date), specificAvailability.getDate());
        assertEquals(java.time.LocalTime.parse(startTime), specificAvailability.getStartTime());
        assertEquals(java.time.LocalTime.parse(endTime), specificAvailability.getEndTime());
        assertNull(specificAvailability.getLawyer());
    }
}
