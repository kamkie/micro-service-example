package net.devopssolutions.microservice.client.controller;

import net.devopssolutions.microservice.model.User;
import net.devopssolutions.microservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
public class ConsumerController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public ResponseEntity<User> consume(HttpServletRequest request) {
        String username = request.getRemoteUser();
        if (username == null) {
            username = "user";
        }
        return ResponseEntity.ok(userService.getUserByName(username));
    }

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }
}
