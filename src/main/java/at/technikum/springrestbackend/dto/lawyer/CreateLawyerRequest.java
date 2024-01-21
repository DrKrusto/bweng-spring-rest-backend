package at.technikum.springrestbackend.dto.lawyer;

import at.technikum.springrestbackend.model.Specialization;
import at.technikum.springrestbackend.model.Lawyer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class CreateLawyerRequest {
    private String firstName;
    private String lastName;
    private Specialization specialization;
    private int hourlyRate;
    private String address;
    private String postalCode;
    private String city;
    private String photoBucket;
    private String photoName;

    public Lawyer toLawyer() {
        Lawyer lawyer = new Lawyer();
        lawyer.setId(UUID.randomUUID());
        lawyer.setFirstName(firstName);
        lawyer.setLastName(lastName);
        lawyer.setSpecialization(specialization.toString());
        lawyer.setHourlyRate(hourlyRate);
        lawyer.setAddress(address);
        lawyer.setPostalCode(postalCode);
        lawyer.setCity(city);
        lawyer.setAvailabilities(new ArrayList<>());
        lawyer.setPhotoBucket(photoBucket);
        lawyer.setPhotoName(photoName);
        return lawyer;
    }
}
