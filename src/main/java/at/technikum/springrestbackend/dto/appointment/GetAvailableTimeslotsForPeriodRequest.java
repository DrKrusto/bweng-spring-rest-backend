package at.technikum.springrestbackend.dto.appointment;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class GetAvailableTimeslotsForPeriodRequest {
    private UUID lawyerId;
    private String startDate;
    private int numberOfDays;
}
