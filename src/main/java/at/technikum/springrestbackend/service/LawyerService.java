package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.DaySchedule;
import at.technikum.springrestbackend.model.GeneralAvailability;
import at.technikum.springrestbackend.model.Lawyer;
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

    public ResponseEntity<List<DaySchedule>> getLawyerAvailabilityForPeriod(UUID id, Date startDay, int days) {
        Optional<Lawyer> lawyerOptional = lawyerRepository.findById(id);
        if (lawyerOptional.isPresent()) {
            Lawyer lawyer = lawyerOptional.get();

            List<DaySchedule> availabilities = new ArrayList<>();
            for (int i = 0; i < days; i++) {
                LocalDate day = LocalDate.ofInstant(startDay.toInstant(), TimeZone.getDefault().toZoneId()).plusDays(i);
                List<LocalTime> availableTimes = new ArrayList<>();

                //todo: get availabilities from database

                availabilities.add(new DaySchedule(day, availableTimes));
            }

            return new ResponseEntity<>(availabilities, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
