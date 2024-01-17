package at.technikum.springrestbackend.dto.appointment;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class CreateAppointmentRequest {
    private UUID lawyerId;
    private UUID userId;
    private String date;
    private String time;
}
