package net.devopssolutions.microservice.security;

import net.devopssolutions.microservice.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthDetailsRestService implements UserDetailsService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = restTemplate.getForEntity("http://austserver/users/getByName/{name}", User.class, username).getBody();
        if (user == null) {
            throw new UsernameNotFoundException(String.format("user %s not found", username));
        }
        return user;
    }
}
