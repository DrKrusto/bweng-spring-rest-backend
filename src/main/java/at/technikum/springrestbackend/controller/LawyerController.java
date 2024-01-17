package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.lawyer.CreateLawyerRequest;
import at.technikum.springrestbackend.dto.lawyer.GetLawyerProfilesRequest;
import at.technikum.springrestbackend.dto.lawyer.LawyerSearchResult;
import at.technikum.springrestbackend.dto.PagedResults;
import at.technikum.springrestbackend.model.Lawyer;
import at.technikum.springrestbackend.service.LawyerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/lawyers")
public class LawyerController {

    private final LawyerService lawyerService;
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Lawyer> createLawyer(@RequestBody CreateLawyerRequest lawyerRequest) {
        return lawyerService.createLawyer(lawyerRequest.toLawyer());
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Lawyer>> getAllLawyers() {
        return lawyerService.getAllLawyers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasPermission(#id, 'at.technikum.springrestbackend.model.Lawyer', 'read')")
    public ResponseEntity<Lawyer> getLawyerById(@PathVariable UUID id) {
        return lawyerService.getLawyerById(id);
    }

    @GetMapping("/search")
    public ResponseEntity<PagedResults<LawyerSearchResult>> getLawyersProfilesBySearchTerm(
            GetLawyerProfilesRequest request)
    {
        return lawyerService.getLawyersProfilesBySearchTerm(request.getSearchTerm(), request.getPage(), request.getSize());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasPermission(#id, 'at.technikum.springrestbackend.model.Lawyer', 'write')")
    public ResponseEntity<Lawyer> updateLawyer(@PathVariable UUID id, @RequestBody Lawyer updatedLawyer) {
        return lawyerService.updateLawyer(id, updatedLawyer);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasPermission(#id, 'at.technikum.springrestbackend.model.Lawyer', 'delete')")
    public ResponseEntity<Void> deleteLawyer(@PathVariable UUID id) {
        return lawyerService.deleteLawyer(id);
    }
}
