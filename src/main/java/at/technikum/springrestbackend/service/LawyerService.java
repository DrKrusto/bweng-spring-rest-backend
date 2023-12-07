package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.DaySchedule;
import at.technikum.springrestbackend.dto.DaySchedules;
import at.technikum.springrestbackend.dto.TimeSlot;
import at.technikum.springrestbackend.model.Appointment;
import at.technikum.springrestbackend.model.GeneralAvailability;
import at.technikum.springrestbackend.model.Lawyer;
import at.technikum.springrestbackend.model.SpecificAvailability;
import at.technikum.springrestbackend.repository.LawyerRepository;
import lombok.AllArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
@AllArgsConstructor
public class LawyerService {

    private final LawyerRepository lawyerRepository;

    public ResponseEntity<Lawyer> createLawyer(Lawyer lawyer) {
        lawyer.setId(UUID.randomUUID());
        Lawyer savedLawyer = lawyerRepository.save(lawyer);
        return new ResponseEntity<>(savedLawyer, HttpStatus.CREATED);
    }

    public ResponseEntity<List<Lawyer>> getAllLawyers() {
        List<Lawyer> lawyers = lawyerRepository.findAll();
        return ResponseEntity.ok(lawyers);
    }

    public ResponseEntity<Lawyer> getLawyerById(UUID id) {
        Optional<Lawyer> lawyerOptional = lawyerRepository.findById(id);
        if (lawyerOptional.isPresent()) {
            return new ResponseEntity<>(lawyerOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Lawyer> updateLawyer(UUID id, Lawyer lawyer) {
        if (lawyerRepository.existsById(id)) {
            lawyer.setId(id);
            return new ResponseEntity<>(lawyerRepository.save(lawyer), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Void> deleteLawyer(UUID id) {
        if (lawyerRepository.existsById(id)) {
            lawyerRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<DaySchedules> getLawyerAvailableScheduleForPeriod(UUID id, LocalDate startDay, int days) {
        Optional<Lawyer> lawyerOptional = lawyerRepository.findById(id);
        if (lawyerOptional.isPresent()) {
            Lawyer lawyer = lawyerOptional.get();

            List<DaySchedule> availabilities = new ArrayList<>();
            for (int i = 0; i < days; i++) {

                // First we create a new day schedule with the current day
                DaySchedule daySchedule = new DaySchedule(startDay, new ArrayList<>());

                // Then we get all the week availabilities for the current day
                List<GeneralAvailability> weekAvailabilities = lawyer.getWeekAvailabilities();

                // From the week availabilities we create a list of time slots
                List<TimeSlot> possibleAppointments = new ArrayList<>();
                for (GeneralAvailability weekAvailability : weekAvailabilities) {
                    if (weekAvailability.getDay().equals(startDay.getDayOfWeek())) {
                        possibleAppointments.addAll(weekAvailability.toTimeSlots());
                    }
                }

                // Now we filter out all the time slots that should be unavailable
                lawyer.getUnavailabilities().stream()
                        .filter(unavailability -> unavailability.getStartDateTime().toLocalDate().equals(startDay))
                        .forEach(unavailability -> {
                            possibleAppointments.removeIf(timeSlot -> {
                                return timeSlot.isOverlapping(
                                        unavailability.getStartDateTime().toLocalTime(),
                                        unavailability.getEndDateTime().toLocalTime());
                        });
                });

                // Finally we remove the time slots that are already booked
                lawyer.getAppointments().stream()
                        .filter(appointment -> appointment.getStartTime().toLocalDate().equals(startDay))
                        .forEach(appointment -> {
                            possibleAppointments.removeIf(timeSlot -> {
                                return timeSlot.isOverlapping(
                                        appointment.getStartTime().toLocalTime(),
                                        appointment.getEndTime().toLocalTime());
                            });
                        });

                availabilities.add(daySchedule);
            }
            return new ResponseEntity<>(new DaySchedules(availabilities), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
