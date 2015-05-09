package net.devopssolutions.microservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import net.devopssolutions.microservice.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static net.devopssolutions.microservice.util.BasicAuthUtil.getBasicAuthHeaders;

@Service
public class UserService {

    @Autowired
    private UserServiceImpl userService;

    @Value("${services.auth.username}")
    private String username;

    @Value("${services.auth.password}")
    private String password;

    public User getUserByName(String name) {
        return getUserByName(name, username, password);
    }

    public User getUserByName(String name, String username, String password) {
        return getUserByName(name, getBasicAuthHeaders(username, password));
    }

    public User getUserByNameAuthFromRequest(String name) {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(authHeader)) {
            return getUserByName(name);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, request.getHeader(HttpHeaders.AUTHORIZATION));

        return getUserByName(name, headers);
    }

    public User getUserByName(String name, HttpHeaders headers) {
        return userService.getUserByName(name, headers);
    }

    @Component
    public static class UserServiceImpl {
        @SuppressWarnings("SpringJavaAutowiringInspection")
        @Autowired
        private RestTemplate restTemplate;

        private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

        @HystrixCommand(fallbackMethod = "retry")
        public User getUserByName(String name, HttpHeaders headers) {
            logger.info("get user by name");
            User user = defaultUsers(name, headers);
            if (user != null) {
                return user;
            }
            return restTemplate.exchange("https://authserver/api/users/getByName/{name}", HttpMethod.GET, new HttpEntity<>(headers), User.class, name).getBody();
        }

        @HystrixCommand(fallbackMethod = "defaultUsers")
        public User retry(String name, HttpHeaders headers) {
            return getUserByName(name, headers);
        }

        public User defaultUsers(String name, HttpHeaders headers) {
            logger.info("get user");
            User user = null;
            if ("user".equals(name)) {
                user = User.newBuilder()
                        .withId(0L)
                        .withName("user")
                        .withRole("USER")
                        .withPassword("$2a$10$F6WOdmfvJPjx9YiWdAYQNOmudKgTQtM37TcbNAhHukXKe9De4oSVK")
                        .build();
            }
            if ("admin".equals(name)) {
                user = User.newBuilder()
                        .withId(0L)
                        .withName("admin")
                        .withRole("ADMIN")
                        .withPassword("$2a$10$F6WOdmfvJPjx9YiWdAYQNOmudKgTQtM37TcbNAhHukXKe9De4oSVK")
                        .build();
            }

            return user;
        }
    }

}
