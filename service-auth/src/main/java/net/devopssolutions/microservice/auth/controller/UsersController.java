package net.devopssolutions.microservice.auth.controller;

import net.devopssolutions.microservice.auth.model.User;
import net.devopssolutions.microservice.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/getByName/{name}")
    public ResponseEntity<User> getUserByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(userRepository.findByName(name));
    }

    @RequestMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userRepository.findOne(id));
    }

}
