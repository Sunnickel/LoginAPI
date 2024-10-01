package at.sunnickel.loginapi.service;

import at.sunnickel.loginapi.controller.UserController;
import at.sunnickel.loginapi.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.hibernate.query.sqm.EntityTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.NoSuchAlgorithmException;
import java.util.List;
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
     * Save user user.
     *
     * @param user the user
     * @return the user
     */
    public ResponseEntity<at.sunnickel.loginapi.model.User> registerUser(@RequestBody at.sunnickel.loginapi.model.User user) throws NoSuchAlgorithmException {
        if (userRepository.findById(user.getId()).isPresent()) {
            throw new EntityExistsException("User with id " + user.getId() + " already exists");
        } else if (user.getId() == 0) {
            throw new EntityTypeException("User needs given id", user.getId().toString());
        }
        String password = user.getPassword();
        String name = user.getName();
        user.setToken(Security.tokenize(name + password));
        at.sunnickel.loginapi.model.User newUser = userRepository.save(user);
        return ResponseEntity.ok(newUser);
    }

    public ResponseEntity<String> loginUser(@RequestBody Map<String, Object> login_parameter) throws NoSuchAlgorithmException {
        String token = (String) login_parameter.get("token");

        Long id = UserController.IntToLong(login_parameter.get("id"));

        if (id != null && userRepository.findById(id).isPresent()) {
            at.sunnickel.loginapi.model.User user = userRepository.findById(id).get();
            if (user.getToken().equals(token)) {
                String ottoken = Security.ottokenize(token);
                user.setOTToken(ottoken);
                userRepository.save(user);
                return ResponseEntity.ok(ottoken);
            }
            return ResponseEntity.ok(ResponseEntity.status(HttpStatus.UNAUTHORIZED).toString());
        }
        return ResponseEntity.ok(ResponseEntity.status(HttpStatus.UNAUTHORIZED).toString());
    }

    /**
     * Gets users.
     *
     * @return all users
     */
    public ResponseEntity<List<at.sunnickel.loginapi.model.User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    /**
     * Gets user by ID
     *
     * @Returns User
     */
    public ResponseEntity<at.sunnickel.loginapi.model.User> getUserById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            return ResponseEntity.ok(userRepository.findById(id).get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public class Security {
        public static String tokenize(String plain_passwd) throws NoSuchAlgorithmException {
            return BCrypt.hashpw(plain_passwd, BCrypt.gensalt(12));
        }

        public static String ottokenize(String plain_passwd) throws NoSuchAlgorithmException {
            return BCrypt.hashpw(plain_passwd + BCrypt.gensalt(), BCrypt.gensalt());
        }

        public boolean check(Long id, String plain_passwd) {
            if (userRepository.findById(id).isPresent()) {
                return BCrypt.checkpw(plain_passwd, userRepository.findById(id).get().getPassword());
            }
            return false;
        }
    }
}
