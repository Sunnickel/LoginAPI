package at.sunnickel.loginapi.service;

import at.sunnickel.loginapi.model.User;
import at.sunnickel.loginapi.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.hibernate.query.sqm.EntityTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

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
     * Registers a new User
     *
     * @param user the Clients user
     *
     * @return the registered User
     */
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            throw new EntityExistsException("User with id " + user.getId() + " already exists");
        } else if (user.getId() == 0) {
            throw new EntityTypeException("User needs given id: " + user.getId(), user.getId().toString());
        }
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        return ResponseEntity.ok(userRepository.save(user));
    }

    /**
     * Checks the User token and gets the One Time Token
     *
     * @param loginParameter a map with Client token and Client id
     *
     * @return One Time Token
     */
    public ResponseEntity<String> loginUser(@RequestBody Map<String, Object> loginParameter) {
        String token = (String) loginParameter.get("token");
        try {
            Long id = Long.valueOf((Integer) loginParameter.get("id"));
            if (userRepository.findById(id).isPresent()) {
                User user = userRepository.findById(id).get();
                if (user.getPassword().equals(token)) {
                    String ottoken = BCrypt.hashpw(token + BCrypt.gensalt(12), BCrypt.gensalt(12));
                    user.setOTToken(ottoken);
                    userRepository.save(user);
                    return ResponseEntity.ok(ottoken);
                }
            }
        } catch (Exception ignored) {
        }
        return ResponseEntity.ok(ResponseEntity.status(HttpStatus.UNAUTHORIZED).toString());
    }


    /**
     * Gets a user by ID
     *
     * @return User
     */
    public ResponseEntity<User> getUserById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            return ResponseEntity.ok(userRepository.findById(id).get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
