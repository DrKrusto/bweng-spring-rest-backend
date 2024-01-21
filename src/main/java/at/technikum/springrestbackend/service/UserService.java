package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.model.User;
import at.technikum.springrestbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<User> createUser(User user) {
        if(!isValidPassword(user.getPassword())){
            return ResponseEntity.badRequest().build();
        }

        user.setId(UUID.randomUUID());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }

    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    public ResponseEntity<User> getUserById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Void> deleteUser(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<User> updateUser(UUID id, User updatedUser) {
        if (userRepository.existsById(id)) {
            updatedUser.setId(id);
            updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            return ResponseEntity.ok(userRepository.save(updatedUser));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByUsernameOrEmail(String username, String email){
        return userRepository.findByUsernameOrEmail(username, email);
    }

    public User updateUsername(UUID id, User user) {
        User userToUpdate = userRepository.findById(id).orElseThrow();
        userToUpdate.setUsername(user.getUsername());
        return userRepository.save(userToUpdate);
    }

    private boolean isValidPassword(String password){
        return password != null &&
                password.length() >= 12 &&
                password.matches(".*[a-z].*") &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[!@#$%^&*()].*");
    }

}
