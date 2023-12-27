package at.technikum.springrestbackend.model;

import at.technikum.springrestbackend.dto.TimeSlot;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@Entity
public class GeneralAvailability {
    @Id
    private UUID id;

    @NotNull
    private DayOfWeek day;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    @NotNull
    private LocalTime appointmentDuration;

    @NotNull
    @ManyToOne
    private Lawyer forLawyer;

    public List<TimeSlot> toTimeSlots() {
        List<TimeSlot> possibleAppointments = new ArrayList<>();
        LocalTime currentStartTime = startTime;
        LocalTime currentEndTime = startTime.plusMinutes(appointmentDuration.getMinute());

        while (currentEndTime.isBefore(endTime) || currentEndTime.equals(endTime)) {
            possibleAppointments.add(new TimeSlot(currentStartTime, currentEndTime));
            currentStartTime = currentEndTime;
            currentEndTime = currentStartTime.plusMinutes(appointmentDuration.getMinute());
        }

        return possibleAppointments;
    }
}
