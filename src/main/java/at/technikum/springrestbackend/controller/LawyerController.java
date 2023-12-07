package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.DaySchedule;
import at.technikum.springrestbackend.dto.DaySchedules;
import at.technikum.springrestbackend.model.Lawyer;
import at.technikum.springrestbackend.service.LawyerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/lawyers")
public class LawyerController {

    private final LawyerService lawyerService;

    // Create a new lawyer
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Lawyer> createLawyer(@RequestBody Lawyer lawyer) {
        return lawyerService.createLawyer(lawyer);
    }

    // Retrieve all lawyers
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Lawyer>> getAllLawyers() {
        return lawyerService.getAllLawyers();
    }

    // Retrieve a single lawyer by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'at.technikum.springrestbackend.model.Lawyer', 'read') OR hasRole('ROLE_ADMIN')")
    public ResponseEntity<Lawyer> getLawyerById(@PathVariable UUID id) {
        return lawyerService.getLawyerById(id);
    }

    // Update a lawyer by ID
    @PutMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'at.technikum.springrestbackend.model.Lawyer', 'write') OR hasRole('ROLE_ADMIN')")
    public ResponseEntity<Lawyer> updateLawyer(@PathVariable UUID id, @RequestBody Lawyer updatedLawyer) {
        return lawyerService.updateLawyer(id, updatedLawyer);
    }

    // Delete a lawyer by ID
    @DeleteMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'at.technikum.springrestbackend.model.Lawyer', 'delete') OR hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteLawyer(@PathVariable UUID id) {
        return lawyerService.deleteLawyer(id);
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<DaySchedules> getLawyerAvailability(
            @PathVariable UUID id,
            @RequestBody LocalDate startDate,
            int days) {
        return lawyerService.getLawyerAvailableScheduleForPeriod(id, startDate, days);
    }
}
