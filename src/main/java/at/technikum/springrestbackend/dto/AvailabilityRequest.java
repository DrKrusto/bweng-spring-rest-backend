package at.technikum.springrestbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class AvailabilityRequest {
    private LocalDate startDate;
    int days;
}
