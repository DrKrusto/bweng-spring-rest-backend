package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.model.Lawyer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

public interface LawyerRepository extends CrudRepository<Lawyer, UUID>, PagingAndSortingRepository<Lawyer, UUID> {

    @Override
    List<Lawyer> findAll();

    Page<Lawyer> findAllByFirstNameContainingOrLastNameContainingOrAddressContainingOrCityContainingOrPostalCodeContainingOrSpecializationContaining(
            String firstName,
            String lastName,
            String address,
            String city,
            String postalCode,
            String specialization,
            Pageable paging
    );
}
