package at.technikum.springrestbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Currency;
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

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotNull
    @ManyToOne
    private Specialization specialization;

    @NotBlank
    private String address;

    @NotBlank
    private String postalCode;

    @NotBlank
    private String city;

    @NotBlank
    private String country;

    @NotNull
    private String appointmentDuration;

    @NotNull
    private Currency feePerHour;

    @Size(min = 1, message = "At least one payment method must be specified")
    @OneToMany
    private List<PaymentMethod> paymentMethods;

    @OneToMany
    private List<GeneralAvailability> weekAvailabilities;

    @OneToMany
    private List<SpecificAvailability> unavailabilities;

    @OneToMany
    private List<Appointment> appointments;

    @Size(max = 500, message = "Description must not be longer than 500 characters")
    private String description;
}
