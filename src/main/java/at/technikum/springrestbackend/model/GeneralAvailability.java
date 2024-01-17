package at.technikum.springrestbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class GeneralAvailability {
    @Id
    private UUID id;

    @NotNull
    private DayOfWeek day;

    @NotNull
    private int duration;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    @NotNull
    @ManyToOne
    private Lawyer lawyer;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant lastUpdatedOn;
}
