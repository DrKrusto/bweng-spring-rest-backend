package at.technikum.springrestbackend.dto.availability;

import at.technikum.springrestbackend.model.GeneralAvailability;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class CreateGeneralAvailabilityRequest {
    private UUID lawyerId;
    private DayOfWeek day;
    private int duration;
    private String startTime;
    private String endTime;

    public GeneralAvailability toGeneralAvailability() {
        GeneralAvailability generalAvailability = new GeneralAvailability();
        generalAvailability.setId(UUID.randomUUID());
        generalAvailability.setDay(day);
        generalAvailability.setDuration(duration);
        generalAvailability.setStartTime(LocalTime.parse(startTime));
        generalAvailability.setEndTime(LocalTime.parse(endTime));
        generalAvailability.setLawyer(null);
        return generalAvailability;
    }
}
