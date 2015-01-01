package net.devopssolutions.microservice.client.controller;

import net.devopssolutions.microservice.auth.api.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/")
    public ResponseEntity<User> consume() {
        User user = restTemplate.getForEntity("http://{user}:{password}@authserver/api/users/getByName/{name}", User.class, "kamil", "dupa", "kamkie").getBody();
        return ResponseEntity.ok(user);
    }
}
