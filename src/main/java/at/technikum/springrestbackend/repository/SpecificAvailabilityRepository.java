package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.model.Lawyer;
import at.technikum.springrestbackend.model.SpecificAvailability;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface SpecificAvailabilityRepository extends CrudRepository<SpecificAvailability, UUID> {
    @Override
    List<SpecificAvailability> findAll();

    List<SpecificAvailability> findByLawyer(Lawyer lawyer);

    List<SpecificAvailability> findAllByDateAndLawyer(LocalDate date, Lawyer lawyer);
}
