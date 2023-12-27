package at.technikum.springrestbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Entity
public class GeneralAvailability {
    @Id
    private UUID id;

    @NotNull(message = "Day must be set")
    private DayOfWeek day;

    @NotNull(message = "Start time must be set")
    private LocalTime startTime;

    @NotNull(message = "End time must be set")
    private LocalTime endTime;

    @NotNull(message = "Lawyer must be set")
    @ManyToOne
    private Lawyer forLawyer;
}
