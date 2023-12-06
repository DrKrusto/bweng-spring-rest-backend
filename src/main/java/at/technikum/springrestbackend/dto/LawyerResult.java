package at.technikum.springrestbackend.dto;

import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class LawyerResult {
    private UUID id;
    private String fullName;
    private String address;
    private String zipCode;
    private String city;
    private String country;
    private String profilePicture;
    private String specialization;
    private String description;
    private List<Date> availabilities;
}
