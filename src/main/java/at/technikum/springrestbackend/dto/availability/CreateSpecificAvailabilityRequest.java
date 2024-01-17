package at.technikum.springrestbackend.dto.availability;

import at.technikum.springrestbackend.model.SpecificAvailability;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class CreateSpecificAvailabilityRequest {
    private UUID lawyerId;
    private String date;
    private String startTime;
    private String endTime;

    public SpecificAvailability toSpecificAvailability() {
        SpecificAvailability specificAvailability = new SpecificAvailability();
        specificAvailability.setId(UUID.randomUUID());
        specificAvailability.setDate(java.time.LocalDate.parse(date));
        specificAvailability.setStartTime(java.time.LocalTime.parse(startTime));
        specificAvailability.setEndTime(java.time.LocalTime.parse(endTime));
        specificAvailability.setLawyer(null);
        return specificAvailability;
    }
}
