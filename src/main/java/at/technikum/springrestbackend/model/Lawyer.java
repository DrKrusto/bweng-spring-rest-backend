package at.technikum.springrestbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Lawyer {
    @Id
    private UUID id;

    @NotBlank
    @Size(min = 2, max = 50)
    private String firstName;

    @Size(min = 2, max = 50)
    private String lastName;

    @NotNull
    private String specialization;

    @NotNull
    private Number hourlyRate;

    @NotBlank
    private String address;

    @NotBlank
    private String postalCode;

    @NotBlank
    private String city;

    @OneToMany
    private List<GeneralAvailability> availabilities;

    private String photoBucket;

    private String photoName;

}
