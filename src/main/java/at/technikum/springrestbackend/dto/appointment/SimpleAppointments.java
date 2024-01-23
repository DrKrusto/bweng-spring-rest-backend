package at.technikum.springrestbackend.dto.appointment;

import at.technikum.springrestbackend.model.Appointment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SimpleAppointments {
    private List<SimpleAppointment> appointments;

    public static SimpleAppointments from(List<Appointment> appointments) {
        return new SimpleAppointments(
                appointments.stream().map(SimpleAppointment::from).toList()
        );
    }
}
