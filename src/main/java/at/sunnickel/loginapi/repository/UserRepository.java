package at.sunnickel.loginapi.repository;

import at.sunnickel.loginapi.model.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

/**
 * The interface User repository.
 */
public interface UserRepository extends ListCrudRepository<User, Long> {
    User findById(UUID id);
}
