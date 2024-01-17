package at.technikum.springrestbackend.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class TokenResponse {
    private String token;
    private UUID id;
}
