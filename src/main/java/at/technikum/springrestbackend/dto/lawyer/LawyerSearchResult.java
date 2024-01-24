package at.technikum.springrestbackend.dto.lawyer;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class LawyerSearchResult {
    private UUID id;
    private String firstName;
    private String lastName;
    private String specialization;
    private int hourlyRate;
    private String address;
    private String postalCode;
    private String city;
    private String photoBucket;
    private String photoName;
    private LawyerAvailability availableSlots;
}
