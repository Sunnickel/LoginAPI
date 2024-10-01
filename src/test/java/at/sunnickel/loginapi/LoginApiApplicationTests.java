package at.sunnickel.loginapi;

import at.sunnickel.loginapi.controller.Controller;
import at.sunnickel.loginapi.repository.ServerRepository;
import at.sunnickel.loginapi.repository.UserRepository;
import at.sunnickel.loginapi.service.ServerService;
import at.sunnickel.loginapi.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoginApiApplicationTests {

    @Autowired
    private Controller controller;

    @Autowired
    private ServerRepository serverRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ServerService serverService;


    @Test
    void contextLoads() {
        Assertions.assertNotNull(controller);
        Assertions.assertNotNull(serverRepository);
        Assertions.assertNotNull(userRepository);
        Assertions.assertNotNull(userService);
        Assertions.assertNotNull(serverService);
    }

}
