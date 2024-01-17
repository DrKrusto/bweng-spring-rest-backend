package at.technikum.springrestbackend.dto;

import at.technikum.springrestbackend.dto.appointment.GetAvailableTimeslotsForPeriodRequest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetAvailableTimeslotsForPeriodRequestTest {

    @Test
    void gettersAndSetters_ShouldWorkAsExpected() {
        // Arrange
        GetAvailableTimeslotsForPeriodRequest request = new GetAvailableTimeslotsForPeriodRequest();
        UUID lawyerId = UUID.randomUUID();
        String startDate = "2022-01-01";
        int numberOfDays = 5;

        // Act
        request.setLawyerId(lawyerId);
        request.setStartDate(startDate);
        request.setNumberOfDays(numberOfDays);

        // Assert
        assertEquals(lawyerId, request.getLawyerId());
        assertEquals(startDate, request.getStartDate());
        assertEquals(numberOfDays, request.getNumberOfDays());
    }

    @Test
    void allArgsConstructor_ShouldSetFields() {
        // Arrange
        UUID lawyerId = UUID.randomUUID();
        String startDate = "2022-01-01";
        int numberOfDays = 5;

        // Act
        GetAvailableTimeslotsForPeriodRequest request = new GetAvailableTimeslotsForPeriodRequest(lawyerId, startDate, numberOfDays);

        // Assert
        assertEquals(lawyerId, request.getLawyerId());
        assertEquals(startDate, request.getStartDate());
        assertEquals(numberOfDays, request.getNumberOfDays());
    }
}
