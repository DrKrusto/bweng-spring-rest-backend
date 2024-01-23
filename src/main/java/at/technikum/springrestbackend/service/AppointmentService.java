package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.appointment.SimpleAppointments;
import at.technikum.springrestbackend.dto.appointment.AvailabilityTimetable;
import at.technikum.springrestbackend.model.*;
import at.technikum.springrestbackend.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
@AllArgsConstructor
public class AppointmentService {
    private final GeneralAvailabilityRepository generalAvailabilityRepository;
    private final SpecificAvailabilityRepository specificAvailabilityRepository;
    private final LawyerRepository lawyerRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    private List<String> getTimeslotsByDate(Lawyer lawyer, LocalDate date) {
        List<Appointment> appointments = appointmentRepository.findAllByLawyerAndDate(lawyer, date);
        List<SpecificAvailability> unavailabilities = specificAvailabilityRepository.findAllByDateAndLawyer(date, lawyer);
        List<GeneralAvailability> generalAvailabilities = generalAvailabilityRepository.findAllByDayAndLawyer(
                date.getDayOfWeek(),
                lawyer
        );

        ArrayList<String> timeslots = new ArrayList<>();
        for (GeneralAvailability availability : generalAvailabilities) {
            LocalTime startTime = availability.getStartTime();
            LocalTime endTime = availability.getEndTime();
            LocalTime currentTime = startTime;

            while (currentTime.isBefore(endTime) || currentTime.equals(endTime)) {
                LocalTime finalCurrentTime = currentTime;

                boolean lawyerIsAvailable = unavailabilities.stream().noneMatch(
                        unavailability -> timeIsBetween(finalCurrentTime, unavailability.getStartTime(), unavailability.getEndTime().minusSeconds(1))
                );

                boolean lawyerHasNoAppointment = appointments.stream().noneMatch(
                        appointment -> timeIsBetween(finalCurrentTime, appointment.getStartTime(), appointment.getEndTime().minusSeconds(1))
                );

                if (lawyerIsAvailable &&
                        lawyerHasNoAppointment &&
                        !currentTime.plusMinutes(availability.getDuration()).isAfter(endTime))
                    timeslots.add(currentTime.toString());

                currentTime = currentTime.plusMinutes(availability.getDuration());
            }
        }
        return timeslots;
    }

    public ResponseEntity<AvailabilityTimetable> getAvailabilityTimeslotsForDates(
            UUID lawyerId,
            LocalDate from,
            int amountOfDays
    ) {
        Optional<Lawyer> optionalLawyer = lawyerRepository.findById(lawyerId);
        if (optionalLawyer.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Lawyer lawyer = optionalLawyer.get();

        TreeMap<String, List<String>> availabilityTimeslots = new TreeMap<>();
        for (int i = 0; i < amountOfDays; i++) {
            LocalDate currentDate = from.plusDays(i);
            List<String> availableTimeslots = getTimeslotsByDate(lawyer, currentDate);
            availabilityTimeslots.put(currentDate.toString(), availableTimeslots);
        }
        return ResponseEntity.ok(new AvailabilityTimetable(availabilityTimeslots));
    }

    public ResponseEntity<Appointment> createAppointment(UUID lawyerId, UUID userId, String date, String time) {
        Optional<Lawyer> lawyer = lawyerRepository.findById(lawyerId);
        if (lawyer.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        List<String> availableTimeslots = getTimeslotsByDate(lawyer.get(), LocalDate.parse(date));
        if (availableTimeslots.stream().noneMatch(timeslot -> LocalTime.parse(time).equals(LocalTime.parse(timeslot)))) {
            return ResponseEntity.badRequest().build();
        }

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        List<GeneralAvailability> generalAvailabilities = generalAvailabilityRepository.findAllByDayAndLawyer(
                LocalDate.parse(date).getDayOfWeek(),
                lawyer.get()
        );
        GeneralAvailability generalAvailability = generalAvailabilities.stream().filter(
                availability ->
                        timeIsBetween(LocalTime.parse(time), availability.getStartTime(), availability.getEndTime())
        ).findFirst().orElse(null);
        if (generalAvailability == null) {
            return ResponseEntity.badRequest().build();
        }

        Appointment newAppointment = new Appointment();
        newAppointment.setId(UUID.randomUUID());
        newAppointment.setLawyer(lawyer.get());
        newAppointment.setUser(user.get());
        newAppointment.setDate(LocalDate.parse(date));
        newAppointment.setStartTime(LocalTime.parse(time));
        newAppointment.setEndTime(LocalTime.parse(time).plusMinutes(generalAvailability.getDuration()));
        return ResponseEntity.ok(appointmentRepository.save(newAppointment));
    }

    private boolean timeIsBetween(LocalTime time, LocalTime startTime, LocalTime endTime) {
        return (time.isAfter(startTime) || time.equals(startTime)) && (time.isBefore(endTime) || time.equals(endTime));
    }

    public ResponseEntity<SimpleAppointments> getAllAppointmentsForUser(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<Appointment> appointments = appointmentRepository.findAllByUser(user.get());
        appointments = appointments
                .stream()
                .sorted(Comparator.comparing(Appointment::getDate).thenComparing(Appointment::getStartTime))
                .toList();
        return ResponseEntity.ok(SimpleAppointments.from(appointments));
    }

    public ResponseEntity<SimpleAppointments> getAllAppointmentsForUserFrom(UUID id, LocalDate from) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<Appointment> appointments = appointmentRepository.findAllByUser(user.get());
        appointments = appointments
                .stream()
                .sorted(Comparator.comparing(Appointment::getDate).thenComparing(Appointment::getStartTime))
                .filter(appointment -> appointment.getDate().isAfter(from) || appointment.getDate().isEqual(from))
                .toList();
        return ResponseEntity.ok(SimpleAppointments.from(appointments));
    }

    public ResponseEntity<SimpleAppointments> getAllAppointmentsForLawyer(UUID id) {
        Optional<Lawyer> lawyer = lawyerRepository.findById(id);
        if (lawyer.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<Appointment> appointments = appointmentRepository.findAllByLawyer(lawyer.get());
        appointments = appointments
                .stream()
                .sorted(Comparator.comparing(Appointment::getDate).thenComparing(Appointment::getStartTime))
                .toList();
        return ResponseEntity.ok(SimpleAppointments.from(appointments));
    }

    public ResponseEntity<SimpleAppointments> getAllAppointmentsForLawyerFrom(UUID id, LocalDate from) {
        Optional<Lawyer> lawyer = lawyerRepository.findById(id);
        if (lawyer.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<Appointment> appointments = appointmentRepository.findAllByLawyer(lawyer.get());
        appointments = appointments
                .stream()
                .sorted(Comparator.comparing(Appointment::getDate).thenComparing(Appointment::getStartTime))
                .filter(appointment -> appointment.getDate().isAfter(from) || appointment.getDate().isEqual(from))
                .toList();
        return ResponseEntity.ok(SimpleAppointments.from(appointments));
    }

    public ResponseEntity<Void> deleteAppointment(UUID id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if (appointment.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        appointmentRepository.delete(appointment.get());
        return ResponseEntity.ok().build();
    }
}
