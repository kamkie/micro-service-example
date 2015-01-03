package net.devopssolutions.microservice.client.controller;

import net.devopssolutions.microservice.client.model.User;
import net.devopssolutions.microservice.client.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ConsumerController {

    @Autowired
    UserService userService;

    @RequestMapping("/")
    public ResponseEntity<User> consume(HttpServletRequest request) {
        String username = request.getRemoteUser();
        if (username == null) {
            username = "user";
        }
        return ResponseEntity.ok(userService.getUserByName(username));
    }
}
