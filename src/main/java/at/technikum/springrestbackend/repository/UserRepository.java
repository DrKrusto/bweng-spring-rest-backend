package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {

    @Override
    List<User> findAll();

    @Override
    Optional<User> findById(UUID uuid);

    User findByUsername(String username);

    User findByUsernameOrEmail(String username, String email);
}
