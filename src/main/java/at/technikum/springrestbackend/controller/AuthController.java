package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.auth.TokenRequest;
import at.technikum.springrestbackend.dto.auth.TokenResponse;
import at.technikum.springrestbackend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public TokenResponse token(@RequestBody @Valid TokenRequest tokenRequest) {
        return authService.authenticate(tokenRequest);
    }
}
