package at.technikum.springrestbackend.dto;

import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public class DaySchedule {
    LocalDate day;
    List<TimeSlot> availableTimes;
}