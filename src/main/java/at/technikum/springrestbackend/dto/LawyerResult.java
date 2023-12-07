package at.technikum.springrestbackend.dto;

import at.technikum.springrestbackend.model.Lawyer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
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
    private int hourlyFee;
    private List<DaySchedule> availabilities;

    public static LawyerResult fromLawyer(Lawyer lawyer, Date startDate, int days) {
        return new LawyerResult(
                lawyer.getId(),
                lawyer.getFirstName() + " " + lawyer.getLastName(),
                lawyer.getAddress(),
                lawyer.getPostalCode(),
                lawyer.getCity(),
                lawyer.getCountry(),
                "https://picsum.photos/200",
                lawyer.getSpecialization().getLabel(),
                lawyer.getDescription(),
                lawyer.getFeePerHour().getNumericCode(),
                new ArrayList<>() //todo: get availabilities
        );
    }
}
