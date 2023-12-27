package at.technikum.springrestbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
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
public class
User {
    @Id
    private UUID id;

    @NotBlank(message = "Username must be set")
    private String username;

    @NotBlank(message = "First name must be set")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters long")
    private String firstName;

    @NotBlank(message = "Last name must be set")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters long")
    private String lastName;

    @NotBlank(message = "Email must be set")
    @Email
    private String email;

    @NotBlank(message = "Password must be set")
    private String password;

    @NotBlank(message = "Role must be set")
    private String role;

    @NotNull(message = "Payment method must be set")
    @OneToMany
    private List<Appointment> appointments;
}
