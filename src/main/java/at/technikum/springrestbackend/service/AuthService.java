package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.auth.TokenRequest;
import at.technikum.springrestbackend.dto.auth.TokenResponse;
import at.technikum.springrestbackend.security.TokenIssuer;
import at.technikum.springrestbackend.security.user.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final TokenIssuer tokenIssuer;

    private final AuthenticationManager authenticationManager;

    public TokenResponse authenticate(TokenRequest tokenRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(tokenRequest.getUsername(), tokenRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        String token = tokenIssuer.issue(principal.getId(), principal.getUsername(), principal.getRole());
        UUID id = principal.getId();
        return new TokenResponse(token, id);
    }
}
