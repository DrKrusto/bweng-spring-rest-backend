package at.technikum.springrestbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
public class SpecificAvailability {
    @Id
    private UUID id;

    @NotNull(message = "Start date time must be set")
    private LocalDateTime startDateTime;

    @NotNull(message = "End date time must be set")
    private LocalDateTime endDateTime;

    @NotNull(message = "Lawyer must be set")
    @ManyToOne
    private Lawyer forLawyer;
}
