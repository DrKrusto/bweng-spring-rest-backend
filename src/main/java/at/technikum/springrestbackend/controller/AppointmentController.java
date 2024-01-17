package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.appointment.CreateAppointmentRequest;
import at.technikum.springrestbackend.dto.appointment.GetAvailableTimeslotsForPeriodRequest;
import at.technikum.springrestbackend.dto.appointment.AvailabilityTimetable;
import at.technikum.springrestbackend.model.Appointment;
import at.technikum.springrestbackend.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody CreateAppointmentRequest request) {
        return appointmentService.createAppointment(
                request.getLawyerId(),
                request.getUserId(),
                request.getDate(),
                request.getTime()
        );
    }

    @GetMapping("/available-timeslots")
    public ResponseEntity<AvailabilityTimetable> getAvailableTimeslotsForPeriod(
            GetAvailableTimeslotsForPeriodRequest request
    ) {
            return appointmentService.getAvailabilityTimeslotsForDates(
                request.getLawyerId(),
                LocalDate.parse(request.getStartDate()),
                request.getNumberOfDays()
        );
    }
}
