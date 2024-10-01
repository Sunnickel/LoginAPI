package at.sunnickel.loginapi.controller;

import at.sunnickel.loginapi.model.Server;
import at.sunnickel.loginapi.model.User;
import at.sunnickel.loginapi.service.ServerService;
import at.sunnickel.loginapi.service.UserService;
import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("")
public class Controller {
    private final UserService userService;
    private final ServerService serverService;

    public Controller(final UserService userService, ServerService serverService) {
        this.userService = userService;
        this.serverService = serverService;
    }


    /**
     * Register a new User
     * <hr>
     * Example:
     * {@code {"id": 0, "name": "name", "password: "passwd"} }
     * <table>
     *   <tr>
     *     <td> id </td> <td> {@link User#id} </td>
     *   </tr>
     *   <tr>
     *     <td> name </td> <td> {@link  User#name}</td>
     *   </tr>
     *   <tr>
     *     <td> password </td> <td> {@link  User#password}</td>
     *   </tr>
     * </table>
     *
     * @param User the User to create
     *
     * @return the ResponseEntity with status 200 (OK) and with body of the new User
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody User User) {
        HashMap<String, Object> body = new HashMap<>();
        User newUser = userService.registerUser(User).getBody();
        assert newUser != null;
        body.put("token", newUser.getPassword());
        body.put("id", newUser.getId());
        return ResponseEntity.ok(body);
    }

    /**
     * Login as an existing User
     * <hr>
     * Example:
     * {@code {"id": 0, "token": "abc"} }
     * <table>
     *   <tr>
     *     <td> id </td> <td> {@link User#id} </td>
     *   </tr>
     *   <tr>
     *     <td> token </td> <td> {@link  User#password}</td>
     *   </tr>
     * </table>
     *
     * @param login_parameter the token and id of the user
     *
     * @return On Time Token to connect to server
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, Object> login_parameter) {
        if (login_parameter.get("token") == null || userService.getUserById(Long.valueOf((Integer) login_parameter.get("id"))).getBody() == null) {
            return ResponseHandler.generateResponse("error", HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(Map.of("id", Objects.requireNonNull(userService.getUserById(Long.valueOf((Integer) (login_parameter.get("id")))).getBody()).getId(), "ottoken", Objects.requireNonNull(userService.loginUser(login_parameter).getBody())));
    }

    /**
     * Gets the Server the One Time Token
     * <hr>
     * Example:
     * {@code {"id": 0, "token": "abc"} }
     * <table>
     *   <tr>
     *     <td> id </td> <td> {@link User#id} </td>
     *   </tr>
     *   <tr>
     *     <td> token </td> <td> {@link  Server#token}</td>
     *   </tr>
     * </table>
     *
     * @param verify_parameter the token of the server and the id of the user
     *
     * @return the clients One Time Token and id
     */
    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verify(@RequestBody Map<String, Object> verify_parameter) {
        if (verify_parameter.get("token") == null || verify_parameter.get("id") == null) {
            return ResponseHandler.generateResponse("error", HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(serverService.verifyUser(verify_parameter).getBody());

    }


    /**
     * Registers a Server (Only one possible)
     * <hr>
     * Example:
     * {@code {"password": "abc"} }
     * <table>
     *   <tr>
     *     <td> name </td> <td> {@link  Server#password}</td>
     *   </tr>
     * </table>
     *
     * @param server new Server
     *
     * @return verify token
     */
    @PostMapping("/server/register")
    public ResponseEntity<Map<String, Object>> register_server(@RequestBody Server server) {
        return ResponseEntity.ok(Map.of("token", Objects.requireNonNull(serverService.registerServer(server).getBody()).getToken()));
    }


    /**
     * Handles if an Object is twice in a database
     *
     * @return Error
     */
    @ExceptionHandler({EntityExistsException.class})
    public ResponseEntity<Map<String, Object>> handleEntityNotFound() {
        return ResponseHandler.generateResponse("Entity already exists", HttpStatus.BAD_GATEWAY);
    }

    /**
     * Handles if the hashing fails
     *
     * @return Error
     */
    @ExceptionHandler({NoSuchAlgorithmException.class})
    public ResponseEntity<Map<String, Object>> handleNoSuchAlgorithm() {
        return ResponseHandler.generateResponse("NoSuchAlgorithmException", HttpStatus.BAD_GATEWAY);
    }
}
