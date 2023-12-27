package at.technikum.springrestbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DaySchedules {
    private List<DaySchedule> daySchedules;
}
