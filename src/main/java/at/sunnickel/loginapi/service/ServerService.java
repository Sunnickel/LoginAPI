package at.sunnickel.loginapi.service;

import at.sunnickel.loginapi.controller.ResponseHandler;
import at.sunnickel.loginapi.model.Server;
import at.sunnickel.loginapi.model.User;
import at.sunnickel.loginapi.repository.ServerRepository;
import at.sunnickel.loginapi.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Service
public class ServerService {
    private final ServerRepository serverRepository;
    private final UserRepository userRepository;

    /**
     * Instantiates a new Server service.
     *
     * @param serverRepository the server repository
     * @param userRepository   the user repository
     */
    @Autowired
    public ServerService(ServerRepository serverRepository, UserRepository userRepository) {
        this.serverRepository = serverRepository;
        this.userRepository = userRepository;
    }


    /**
     * Checks the Server token and gets the User One Time Token
     *
     * @param verifyParameter a Map with the server token and client id
     *
     * @return Users One Time Token
     */
    public ResponseEntity<Map<String, Object>> verifyUser(@RequestBody Map<String, Object> verifyParameter) {
        String token = (String) verifyParameter.get("token");
        try {
            Long id = Long.valueOf((Integer) verifyParameter.get("id"));
            if (serverRepository.findById(0L).isPresent() && userRepository.findById(id).isPresent()) {
                Server server = serverRepository.findById(0L).get();
                if (server.getToken().equals(token)) {
                    User user = userRepository.findById(id).get();
                    if (!user.testOTToken()) {
                        return ResponseHandler.generateResponse("No One Time Token available", HttpStatus.BAD_REQUEST);
                    } else {
                        String ottoken = user.getOTToken();
                        userRepository.save(user);
                        return ResponseEntity.ok(Map.of("id", id, "ottoken", ottoken));
                    }
                }
            }
        } catch (Exception ignored) {
        }
        return ResponseHandler.generateResponse("Error", HttpStatus.UNAUTHORIZED);
    }

    /**
     * Registers a Server (Only one)
     *
     * @param server the new server
     *
     * @return saved server
     */
    public ResponseEntity<Server> registerServer(@RequestBody Server server) {
        if (serverRepository.findById(0L).isPresent()) {
            throw new EntityExistsException("There is already a Server registered.");
        }
        server.setToken(BCrypt.hashpw(server.getPassword() + BCrypt.gensalt(), BCrypt.gensalt(12)));
        server.setPassword(BCrypt.hashpw(server.getPassword(), BCrypt.gensalt()));
        return ResponseEntity.ok(serverRepository.save(server));
    }

}
