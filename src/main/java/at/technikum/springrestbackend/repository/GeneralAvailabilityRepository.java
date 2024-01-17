package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.model.GeneralAvailability;
import at.technikum.springrestbackend.model.Lawyer;
import at.technikum.springrestbackend.model.SpecificAvailability;
import org.springframework.data.repository.CrudRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;

public interface GeneralAvailabilityRepository extends CrudRepository<GeneralAvailability, UUID> {

    @Override
    List<GeneralAvailability> findAll();
    List<GeneralAvailability> findByLawyer(Lawyer lawyer);

    List<GeneralAvailability> findAllByDayAndLawyer(DayOfWeek day, Lawyer lawyer);
}
