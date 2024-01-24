package at.technikum.springrestbackend.security.user;

import at.technikum.springrestbackend.model.User;
import at.technikum.springrestbackend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        try {
            User user = userService.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);

            return new UserPrincipal(user.getId(), user.getUsername(), user.getPassword(), user.getRole(), user.getIsLocked());
        } catch (EntityNotFoundException e) {
            throw new UsernameNotFoundException(usernameOrEmail);
        }
    }
}
