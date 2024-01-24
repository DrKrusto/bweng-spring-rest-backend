package at.technikum.springrestbackend.dto.appointment;

import at.technikum.springrestbackend.model.Appointment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class SimpleAppointment {
    private UUID id;
    private UUID lawyerId;
    private String lawyerName;
    private UUID userId;
    private String userName;
    private String date;
    private String time;

    public SimpleAppointment(Appointment appointment) {
        this.id = appointment.getId();
        this.lawyerId = appointment.getLawyer().getId();
        this.lawyerName = appointment.getLawyer().getFirstName() + " " + appointment.getLawyer().getLastName();
        this.userId = appointment.getUser().getId();
        this.userName = appointment.getUser().getFirstName() + " " + appointment.getUser().getLastName();
        this.date = appointment.getDate().toString();
        this.time = appointment.getStartTime().toString();
    }

    public static SimpleAppointment from(Appointment appointment) {
        return new SimpleAppointment(appointment);
    }
}
