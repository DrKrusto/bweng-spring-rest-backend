package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.model.GeneralAvailability;
import at.technikum.springrestbackend.service.GeneralAvailabilityService;
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
public class GeneralAvailabilityControllerIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GeneralAvailabilityService generalAvailabilityService;

    @Autowired
    private ObjectMapper mapper;


    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllAvailabilities_shouldReturnListOfAvailabilities() throws Exception {
        // Arrange
        List<GeneralAvailability> availabilities = Collections.singletonList(new GeneralAvailability());
        when(generalAvailabilityService.getAllAvailabilities()).thenReturn(new ResponseEntity<>(availabilities, HttpStatus.OK));

        // Act and Assert
        mvc.perform(get("/lawyers/general-availabilities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getById_shouldReturnAvailabilityById() throws Exception {
        // Arrange
        UUID availabilityId = UUID.randomUUID();
        GeneralAvailability availability = new GeneralAvailability();
        availability.setId(availabilityId);
        when(generalAvailabilityService.getById(availabilityId)).thenReturn(new ResponseEntity<>(availability, HttpStatus.OK));

        // Act and Assert
        mvc.perform(get("/lawyers/general-availabilities/{id}", availabilityId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(availabilityId.toString()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void update_shouldReturnUpdatedAvailability() throws Exception {
        // Arrange
        UUID availabilityId = UUID.randomUUID();
        GeneralAvailability updatedAvailability = new GeneralAvailability();
        updatedAvailability.setId(availabilityId);
        when(generalAvailabilityService.update(eq(availabilityId), any(GeneralAvailability.class))).thenReturn(new ResponseEntity<>(updatedAvailability, HttpStatus.OK));

        // Act and Assert
        mvc.perform(put("/lawyers/general-availabilities/{id}", availabilityId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updatedAvailability)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(availabilityId.toString()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void delete_shouldReturnNoContent() throws Exception {
        // Arrange
        UUID availabilityId = UUID.randomUUID();
        when(generalAvailabilityService.delete(availabilityId)).thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        // Act and Assert
        mvc.perform(delete("/lawyers/general-availabilities/{id}", availabilityId))
                .andExpect(status().isNoContent());
    }
}
