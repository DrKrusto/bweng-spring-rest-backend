package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.lawyer.CreateLawyerRequest;
import at.technikum.springrestbackend.model.Lawyer;
import at.technikum.springrestbackend.model.Specialization;
import at.technikum.springrestbackend.service.LawyerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class LawyerControllerIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LawyerService lawyerService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    void createLawyer_shouldReturnCreated() throws Exception {
        // Arrange
        CreateLawyerRequest createLawyerRequest = new CreateLawyerRequest(
                "John",
                "Doe",
                Specialization.CRIMINAL_LAW,
                100,
                "123 Main St",
                "12345",
                "City"
        );

        Lawyer newLawyer = createLawyerRequest.toLawyer();
        when(lawyerService.createLawyer(any(Lawyer.class))).thenReturn(new ResponseEntity<>(newLawyer, HttpStatus.CREATED));

        // Act and Assert
        mvc.perform(post("/lawyers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(newLawyer)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllLawyers_shouldReturnListOfLawyers() throws Exception {
        // Arrange
        List<Lawyer> lawyers = Collections.singletonList(new Lawyer());
        when(lawyerService.getAllLawyers()).thenReturn(new ResponseEntity<>(lawyers, HttpStatus.OK));

        // Act and Assert
        mvc.perform(get("/lawyers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getLawyerById_shouldReturnLawyerById() throws Exception {
        // Arrange
        UUID lawyerId = UUID.randomUUID();
        Lawyer lawyer = new Lawyer();
        lawyer.setId(lawyerId);
        when(lawyerService.getLawyerById(lawyerId)).thenReturn(new ResponseEntity<>(lawyer, HttpStatus.OK));

        // Act and Assert
        mvc.perform(get("/lawyers/{id}", lawyerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(lawyerId.toString()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateLawyer_shouldReturnUpdatedLawyer() throws Exception {
        // Arrange
        UUID lawyerId = UUID.randomUUID();
        Lawyer updatedLawyer = new Lawyer();
        updatedLawyer.setId(lawyerId);
        when(lawyerService.updateLawyer(eq(lawyerId), any(Lawyer.class))).thenReturn(new ResponseEntity<>(updatedLawyer, HttpStatus.OK));

        // Act and Assert
        mvc.perform(put("/lawyers/{id}", lawyerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updatedLawyer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(lawyerId.toString()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteLawyer_shouldReturnNoContent() throws Exception {
        // Arrange
        UUID lawyerId = UUID.randomUUID();
        when(lawyerService.deleteLawyer(lawyerId)).thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        // Act and Assert
        mvc.perform(delete("/lawyers/{id}", lawyerId))
                .andExpect(status().isNoContent());
    }


}
