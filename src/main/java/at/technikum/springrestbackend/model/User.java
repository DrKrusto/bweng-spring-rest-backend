package at.technikum.springrestbackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class
User {
    @Id
    private UUID id;

    @NotBlank
    @Size(max = 30)
    private String salutation;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    @Size(min = 5)
    @Column(unique = true)
    private String username;

    @NotBlank
    @Size(min = 2, max = 50)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 50)
    private String lastName;

    @NotBlank
    private String password;

    @Pattern(regexp = "^[A-Z]{2}$", message = "Invalid country code")
    private String countryCode;

    @NotBlank
    private String role;

    @OneToMany
    private List<Appointment> appointments;

    private String photoBucket;

    private String photoName;

    private Boolean isLocked = false;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant lastUpdatedOn;
}
