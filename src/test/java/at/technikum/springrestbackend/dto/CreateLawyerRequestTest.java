package at.technikum.springrestbackend.dto;

import at.technikum.springrestbackend.dto.lawyer.CreateLawyerRequest;
import at.technikum.springrestbackend.model.Specialization;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateLawyerRequestTest {

    @Test
    void gettersAndSetters_WorkCorrectly() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        Specialization specialization = Specialization.CRIMINAL_LAW;
        int hourlyRate = 100;
        String address = "123 Main St";
        String postalCode = "12345";
        String city = "City";

        // Act
        CreateLawyerRequest createLawyerRequest = new CreateLawyerRequest(
                firstName, lastName, specialization, hourlyRate, address, postalCode, city);

        // Assert
        assertEquals(firstName, createLawyerRequest.getFirstName());
        assertEquals(lastName, createLawyerRequest.getLastName());
        assertEquals(specialization, createLawyerRequest.getSpecialization());
        assertEquals(hourlyRate, createLawyerRequest.getHourlyRate());
        assertEquals(address, createLawyerRequest.getAddress());
        assertEquals(postalCode, createLawyerRequest.getPostalCode());
        assertEquals(city, createLawyerRequest.getCity());
    }
}
