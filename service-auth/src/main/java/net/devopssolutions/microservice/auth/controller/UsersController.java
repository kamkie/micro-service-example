package net.devopssolutions.microservice.auth.controller;

import net.devopssolutions.microservice.auth.model.User;
import net.devopssolutions.microservice.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/principal")
    public ResponseEntity<Principal> user(Principal user) {
        return ResponseEntity.ok(user);
    }

    @RequestMapping("/getByName/{name}")
    public ResponseEntity<User> getUserByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(userRepository.findByName(name));
    }

    @RequestMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userRepository.findOne(id));
    }

}
