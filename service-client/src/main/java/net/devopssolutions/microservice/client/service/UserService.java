package net.devopssolutions.microservice.client.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import net.devopssolutions.microservice.client.model.User;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;

@Component
@Scope("request")
public class UserService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    HttpServletRequest request;

    @Value("${services.auth.username}")
    String username;

    @Value("${services.auth.password}")
    String password;

    public User getUserByNameAuthFromConfig(String name) {
        return getUserByName(name, username, password);
    }

    public User getUserByName(String name, String username, String password) {
        HttpHeaders headers = getBasicAuthHeaders(username, password);
        return getUserByName(name, headers);
    }

    public User getUserByNameAuthFromRequest(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, request.getHeader(HttpHeaders.AUTHORIZATION));

        return getUserByName(name, headers);
    }

    @HystrixCommand(fallbackMethod = "defaultUsers")
    public User getUserByName(String name, HttpHeaders headers) {
        return restTemplate.exchange("http://authserver/api/users/getByName/{name}", HttpMethod.GET, new HttpEntity(headers), User.class, name).getBody();
    }

    public User defaultUsers(String name) {
        User user = null;
        if ("user".equals(name)) {
            user = User.newBuilder()
                    .withName("user")
                    .withRole("USER")
                    .withPassword("$2a$10$F6WOdmfvJPjx9YiWdAYQNOmudKgTQtM37TcbNAhHukXKe9De4oSVK")
                    .build();
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
