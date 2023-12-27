package at.technikum.springrestbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class DaySchedule {
    LocalDate day;
    List<TimeSlot> availableTimes;
}