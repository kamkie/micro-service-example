package net.devopssolutions.microservice.client.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import net.devopssolutions.microservice.auth.api.User;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

@Component
public class UserService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    RestTemplate restTemplate;

    @Value("${services.auth.username}")
    String username;

    @Value("${services.auth.password}")
    String password;

    @HystrixCommand(fallbackMethod = "defaultUsers")
    public User getUserByName(String name) {
        HttpHeaders headers = getBasicAuthHeaders(username, password);
        return restTemplate.exchange("http://authserver/api/users/getByName/{name}", HttpMethod.GET, new HttpEntity(headers), User.class, name).getBody();
    }

    public User defaultUsers(String name) {
        User user = null;
        if ("user".equals(name)) {
            user = new User();
            user.setName("user");
            user.setRole("USER");
            user.setPassword("$2a$10$F6WOdmfvJPjx9YiWdAYQNOmudKgTQtM37TcbNAhHukXKe9De4oSVK");
        }

        return user;
    }

    private HttpHeaders getBasicAuthHeaders(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        String auth = username + ":" + password;
        String authHeader = "Basic " + Base64.encodeBase64String(auth.getBytes(Charset.forName("UTF-8")));
        headers.set(HttpHeaders.AUTHORIZATION, authHeader);
        return headers;
    }
}
