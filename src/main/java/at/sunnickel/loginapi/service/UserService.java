package at.sunnickel.loginapi.service;

import at.sunnickel.loginapi.model.User;
import at.sunnickel.loginapi.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

/**
 * The type User service.
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    /**
     * Instantiates a new User service.
     *
     * @param userRepository the user repository
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Save user user.
     *
     * @param user the user
     *
     * @return the user
     */
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        if (user.getId() == null || userRepository.findById(user.getId()) == null) {
            throw new EntityExistsException("User with id " + user.getId() + " already exists");
        }
        User newUser = userRepository.save(user);
        return ResponseEntity.ok(newUser);
    }

    /**
     * Gets users.
     *
     * @return all users
     */
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    /**
     * Gets user by id.
     *
     * @param id the id
     *
     * @return the user by id
     */
    public ResponseEntity<Optional<User>> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Delete user.
     *
     * @param id the userid
     */
    public ResponseEntity<String> deleteUser(Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok("User Deleted Successfully");
    }

    /**
     * Updates the User
     *
     * @param id          the id of the user
     * @param updatedUser the user with new data
     *
     * @return User
     * @throws IllegalArgumentException if id null
     * @throws EntityNotFoundException  if user not in database
     */
    public ResponseEntity<User> updateUser(Long id, User updatedUser) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        User existingUser = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        User savedUser = userRepository.save(existingUser);
        return ResponseEntity.ok(savedUser);
    }
}
