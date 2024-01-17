package at.technikum.springrestbackend.dto.appointment;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.TreeMap;

@AllArgsConstructor
@Getter
public class AvailabilityTimetable {
    private TreeMap<String, List<String>> availabilityTimetable;
}
