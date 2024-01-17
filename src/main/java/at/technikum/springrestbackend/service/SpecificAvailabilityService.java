package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.model.SpecificAvailability;
import at.technikum.springrestbackend.repository.SpecificAvailabilityRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SpecificAvailabilityService {

    private final SpecificAvailabilityRepository specificAvailabilityRepository;

    public ResponseEntity<List<SpecificAvailability>> getAllAvailabilities() {
        List<SpecificAvailability> availabilities = specificAvailabilityRepository.findAll();
        return ResponseEntity.ok(availabilities);
    }

    public ResponseEntity<SpecificAvailability> getAvailabilityById(UUID id) {
        Optional<SpecificAvailability> availability = specificAvailabilityRepository.findById(id);
        return availability.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<SpecificAvailability> createAvailability(SpecificAvailability specificAvailability) {
        SpecificAvailability savedAvailability = specificAvailabilityRepository.save(specificAvailability);
        return new ResponseEntity<>(savedAvailability, HttpStatus.CREATED);
    }

    public ResponseEntity<SpecificAvailability> updateAvailability(UUID id, SpecificAvailability updatedAvailability) {
        if (!specificAvailabilityRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedAvailability.setId(id);
        SpecificAvailability savedAvailability = specificAvailabilityRepository.save(updatedAvailability);
        return ResponseEntity.ok(savedAvailability);
    }

    public ResponseEntity<Void> deleteAvailability(UUID id) {
        if (!specificAvailabilityRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        specificAvailabilityRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
