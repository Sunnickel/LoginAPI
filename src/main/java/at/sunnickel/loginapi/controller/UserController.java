package at.sunnickel.loginapi.controller;

import at.sunnickel.loginapi.model.User;
import at.sunnickel.loginapi.service.UserService;
import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("")
public class UserController {
    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * Converts Integer to Long from a RequestBody
     *
     * @param id Parameters given by the user
     * @return Long ID
     */
    public static Long IntToLong(@RequestBody Object id) {
        Long longID = null;
        if (id instanceof Integer) {
            longID = Long.valueOf((Integer) id);
        } else if (id instanceof Long) {
            longID = (Long) id;
        } else if (id != null) {
            throw new IllegalArgumentException("Invalid type for id");
        }
        return longID;
    }

    /**
     * Register a new User
     *
     * @param User the User to create
     * @return the ResponseEntity with status 200 (OK) and with body of the new User
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("/register")
    public ResponseEntity<HashMap<String, Object>> register(@RequestBody User User) throws NoSuchAlgorithmException {
        HashMap<String, Object> body = new HashMap<>();
        User newUser = userService.registerUser(User).getBody();
        assert newUser != null;
        body.put("token", newUser.getToken());
        body.put("id", newUser.getId());
        return ResponseEntity.ok(body);
    }

    /**
     * Login as a existing User
     *
     * @param login_parameter
     * @return On Time Token to connect to server
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, Object> login_parameter) throws NoSuchAlgorithmException {
        HashMap<String, Object> body = new HashMap<>();
        if (login_parameter.get("token") == null || login_parameter.get("id") == null || userService.getUserById(IntToLong(login_parameter.get("id"))).getBody() == null) {
            return ResponseHandler.generateMapResponse("error", HttpStatus.UNAUTHORIZED);
        }
        Long id = Objects.requireNonNull(userService.getUserById(IntToLong(login_parameter.get("id"))).getBody()).getId();
        String ottoken = Objects.requireNonNull(userService.loginUser(login_parameter).getBody());
        body.put("id", id);
        body.put("ottoken", ottoken);

        return ResponseEntity.ok(body);
    }

    /**
     * Get all Users.
     *
     * @return the ResponseEntity with status 200 (OK) and with body of the list of Users
     */
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers().getBody();
    }


    @ExceptionHandler({EntityExistsException.class})
    public ResponseEntity<Object> handleEntityNotFound(Exception ex) {
        return ResponseHandler.generateObjectResponse("Entity already exists", HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler({NoSuchAlgorithmException.class})
    public ResponseEntity<Object> handleNoSuchAlgorithm(Exception ex) {
        return ResponseHandler.generateObjectResponse("NoSuchAlgorithmException", HttpStatus.BAD_GATEWAY);
    }
}
