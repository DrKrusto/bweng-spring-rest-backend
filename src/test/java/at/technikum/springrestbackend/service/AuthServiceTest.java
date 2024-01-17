package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.auth.TokenRequest;
import at.technikum.springrestbackend.dto.auth.TokenResponse;
import at.technikum.springrestbackend.security.TokenIssuer;
import at.technikum.springrestbackend.security.user.UserPrincipal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private TokenIssuer tokenIssuer;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    void authenticate_shouldIssueToken() {
        // Arrange
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setUsername("username");
        tokenRequest.setPassword("password");
        Authentication authentication = mock(Authentication.class);;
        UserPrincipal principal = new UserPrincipal(UUID.randomUUID(), tokenRequest.getUsername(), tokenRequest.getPassword(), "ROLE_ADMIN");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(principal);

        // Mock the token issuance
        when(tokenIssuer.issue(any(UUID.class), anyString(), anyString())).thenReturn("generatedToken");

        // Act
        TokenResponse tokenResponse = authService.authenticate(tokenRequest);

        // Assert
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenIssuer).issue(principal.getId(), principal.getUsername(), principal.getRole());
        assertEquals("generatedToken", tokenResponse.getToken());
        assertEquals(principal.getId(), tokenResponse.getId());
    }
}
