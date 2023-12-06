package at.technikum.springrestbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Appointment {
    @Id
    private UUID id;

    @FutureOrPresent(message = "Start time must be in the future")
    private LocalDateTime startTime;

    @Future(message = "End time must be in the future")
    private LocalDateTime endTime;

    @NotNull(message = "Lawyer must be set")
    @ManyToOne
    private Lawyer byLawyer;

    @NotNull(message = "User must be set")
    @ManyToOne
    private User forUser;
}