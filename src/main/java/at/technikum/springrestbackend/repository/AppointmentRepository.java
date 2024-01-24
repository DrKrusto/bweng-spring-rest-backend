package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.model.Appointment;
import at.technikum.springrestbackend.model.Lawyer;
import at.technikum.springrestbackend.model.User;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepository extends CrudRepository<Appointment, UUID> {
    List<Appointment> findByUser(User user);

    Optional<Appointment> findByLawyerAndDateAndStartTime(Lawyer lawyer, LocalDate date, LocalTime startTime);

    List<Appointment> findAllByLawyerAndDate(Lawyer lawyer, LocalDate date);

    List<Appointment> findAllByUser(User user);

    List<Appointment> findAllByLawyer(Lawyer lawyer);
}
