package at.technikum.springrestbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
public class TimeSlot {
    private LocalTime startTime;
    private LocalTime endTime;

    public boolean isOverlapping(TimeSlot other) {
        return this.startTime.isBefore(other.endTime) && other.startTime.isBefore(this.endTime);
    }

    public boolean isOverlapping(LocalTime startTime, LocalTime endTime) {
        return this.startTime.isBefore(endTime) && startTime.isBefore(this.endTime);
    }
}
