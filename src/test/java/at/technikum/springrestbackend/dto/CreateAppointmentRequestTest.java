package at.technikum.springrestbackend.dto;

import at.technikum.springrestbackend.dto.appointment.CreateAppointmentRequest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateAppointmentRequestTest {

    @Test
    void constructor_SetsFieldsCorrectly() {
        // Arrange
        UUID lawyerId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String date = "2022-01-01";
        String time = "12:00";

        // Act
        CreateAppointmentRequest request = new CreateAppointmentRequest(
                lawyerId, userId, date, time);

        // Assert
        assertEquals(lawyerId, request.getLawyerId());
        assertEquals(userId, request.getUserId());
        assertEquals(date, request.getDate());
        assertEquals(time, request.getTime());
    }
}
