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

    @NotBlank(message = "First name must be set")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters long")
    private String firstName;

    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters long")
    private String lastName;

    @NotBlank(message = "Email must be set")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password must be set")
    private String password;

    @NotNull(message = "Specialization must be set")
    @ManyToOne
    private Specialization specialization;

    @NotBlank(message = "Address must be set")
    private String address;

    @NotBlank(message = "Postal code must be set")
    private String postalCode;

    @NotBlank(message = "City must be set")
    private String city;

    @NotBlank(message = "Country must be set")
    private String country;

    @NotNull(message = "Appointment duration must be set")
    private String appointmentDuration;

    @NotNull(message = "Fee per hour must be set")
    private Currency feePerHour;

    @Size(min = 1, message = "At least one payment method must be specified")
    @OneToMany
    private List<PaymentMethod> paymentMethods;

    @NotNull(message = "Week availability must be set")
    @OneToMany
    private List<GeneralAvailability> weekAvailabilities;

    @NotNull(message = "Unavailabilities must be set")
    @OneToMany
    private List<SpecificAvailability> unavailabilities;

    @NotNull(message = "Appointments must be set")
    @OneToMany
    private List<Appointment> appointments;
}
