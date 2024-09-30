package at.sunnickel.loginapi.controller;

import at.sunnickel.loginapi.model.User;
import at.sunnickel.loginapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("")
public class UserController {
    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * Create a new User.
     *
     * @param User the User to create
     *
     * @return the ResponseEntity with status 200 (OK) and with body of the new User
     */
    @PostMapping("/User")
    public ResponseEntity<User> saveUser(@RequestBody User User) {
        User newUser = userService.saveUser(User).getBody();
        return ResponseEntity.ok(newUser);
    }

    /**
     * Get all Users.
     *
     * @return the ResponseEntity with status 200 (OK) and with body of the list of Users
     */
    @GetMapping("/Users")
    public List<User> getAllUsers() {
        return userService.getAllUsers().getBody();
    }

    /**
     * Get a User by ID.
     *
     * @param id the ID of the User to get
     *
     * @return the ResponseEntity with status 200 (OK) and with body of the User, or with status 404 (Not Found) if the User does not exist
     */
    @GetMapping("/Users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> User = userService.getUserById(id).getBody();
        assert Objects.requireNonNull(User).isPresent();
        return User.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Update a User by ID.
     *
     * @param id   the ID of the User to update
     * @param User the updated User
     *
     * @return the ResponseEntity with status 200 (OK) and with body of the updated User, or with status 404 (Not Found) if the User does not exist
     */
    @PutMapping("/Users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User User) {
        User updatedUser = userService.updateUser(id, User).getBody();
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Delete a User by ID.
     *
     * @param id the ID of the User to delete
     *
     * @return the ResponseEntity with status 200 (OK) and with body of the message "User deleted successfully"
     */
    @DeleteMapping("/Users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }


    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    public ResponseEntity<Object> handleEntityNotFound(Exception ex) {
        return ResponseHandler.generateResponse("Entity already exists", HttpStatus.BAD_GATEWAY);
    }
}
