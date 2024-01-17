package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.model.User;
import at.technikum.springrestbackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_shouldReturnCreatedUser() {
        // Arrange
        User userToCreate = new User();
        userToCreate.setPassword("NiceValidPW123!");

        // Mock behavior
        when(passwordEncoder.encode(userToCreate.getPassword())).thenReturn("HashedPassword123!");
        when(userRepository.save(any(User.class))).thenReturn(userToCreate);

        // Act
        ResponseEntity<User> response = userService.createUser(userToCreate);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userToCreate, response.getBody());
    }

    @Test
    void createUser_shouldReturnBadRequestForInvalidPassword() {
        // Arrange
        User userWithInvalidPassword = new User();
        userWithInvalidPassword.setPassword("password");

        // Act
        ResponseEntity<User> response = userService.createUser(userWithInvalidPassword);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getAllUsers_shouldReturnListOfUsers() {
        // Arrange
        List<User> expectedUsers = Arrays.asList(
                new User(),
                new User()
        );

        // Mock behavior
        when(userRepository.findAll()).thenReturn(expectedUsers);

        // Act
        ResponseEntity<List<User>> response = userService.getAllUsers();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedUsers, response.getBody());
    }

    @Test
    void getUserById_shouldReturnUserById() {
        // Arrange
        UUID userId = UUID.randomUUID();
        User expectedUser = new User();

        // Mock behavior
        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        // Act
        ResponseEntity<User> response = userService.getUserById(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedUser, response.getBody());
    }

    @Test
    void getUserById_shouldReturnNotFoundForNonExistingId() {
        // Arrange
        UUID nonExistingId = UUID.randomUUID();

        // Mock behavior
        when(userRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<User> response = userService.getUserById(nonExistingId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void deleteUser_shouldReturnNoContentForExistingId() {
        // Arrange
        UUID existingId = UUID.randomUUID();

        // Mock behavior
        when(userRepository.existsById(existingId)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = userService.deleteUser(existingId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void deleteUser_shouldReturnNotFoundForNonExistingId() {
        // Arrange
        UUID nonExistingId = UUID.randomUUID();

        // Mock behavior
        when(userRepository.existsById(nonExistingId)).thenReturn(false);

        // Act
        ResponseEntity<Void> response = userService.deleteUser(nonExistingId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void updateUser_shouldReturnUpdatedUser() {
        // Arrange
        UUID userId = UUID.randomUUID();
        User updatedUser = new User();
        updatedUser.setPassword("NiceValidPW123!");

        // Mock behavior
        when(userRepository.existsById(userId)).thenReturn(true);
        when(passwordEncoder.encode(updatedUser.getPassword())).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        // Act
        ResponseEntity<User> response = userService.updateUser(userId, updatedUser);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedUser, response.getBody());
    }

    @Test
    void updateUser_shouldReturnNotFoundForNonExistingId() {
        // Arrange
        UUID nonExistingId = UUID.randomUUID();
        User updatedUser = new User();

        // Mock behavior
        when(userRepository.existsById(nonExistingId)).thenReturn(false);

        // Act
        ResponseEntity<User> response = userService.updateUser(nonExistingId, updatedUser);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void findByUsername_shouldReturnUser() {
        // Arrange
        String username = "testUser";
        User expectedUser = new User();

        // Mock behavior
        when(userRepository.findByUsername(username)).thenReturn(expectedUser);

        // Act
        User result = userService.findByUsername(username);

        // Assert
        assertNotNull(result);
        assertEquals(expectedUser, result);
    }

    @Test
    void updateUsername_shouldReturnUpdatedUser() {
        // Arrange
        UUID userId = UUID.randomUUID();
        User userToUpdate = new User();
        User updatedUser = new User();

        // Mock behavior
        when(userRepository.findById(userId)).thenReturn(Optional.of(userToUpdate));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        // Act
        User result = userService.updateUsername(userId, updatedUser);

        // Assert
        assertNotNull(result);
        assertEquals(updatedUser, result);
    }

    @Test
    void updateUsername_shouldThrowExceptionForNonExistingId() {
        // Arrange
        UUID nonExistingId = UUID.randomUUID();
        User updatedUser = new User();

        // Mock behavior
        when(userRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Assert
        assertThrows(NoSuchElementException.class, () -> userService.updateUsername(nonExistingId, updatedUser));
    }
}
