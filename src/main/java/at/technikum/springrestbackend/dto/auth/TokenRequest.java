package at.technikum.springrestbackend.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
