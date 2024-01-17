package at.technikum.springrestbackend.dto.appointment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetAvailableTimeslotsForPeriodRequest {
    private UUID lawyerId;
    private String startDate;
    private int numberOfDays;
}
