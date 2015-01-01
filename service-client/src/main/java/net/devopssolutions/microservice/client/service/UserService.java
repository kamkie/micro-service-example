package net.devopssolutions.microservice.client.service;

import com.sun.jersey.core.util.Base64;
import net.devopssolutions.microservice.auth.api.User;
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

    public User getUserByName(String name) {
        HttpHeaders headers = getBasicAuthHeaders(username, password);
        return restTemplate.exchange("http://authserver/api/users/getByName/{name}", HttpMethod.GET, new HttpEntity(headers), User.class, name).getBody();
    }

    private HttpHeaders getBasicAuthHeaders(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encode(auth.getBytes(Charset.forName("UTF-8")));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set(HttpHeaders.AUTHORIZATION, authHeader);
        return headers;
    }
}
