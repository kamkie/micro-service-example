package net.devopssolutions.microservice.client.controller;

import net.devopssolutions.microservice.auth.api.User;
import net.devopssolutions.microservice.client.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

    @Autowired
    UserService userService;

    @RequestMapping("/")
    public ResponseEntity<User> consume() {
        return ResponseEntity.ok(userService.getUserByName("kamil"));
    }
}
