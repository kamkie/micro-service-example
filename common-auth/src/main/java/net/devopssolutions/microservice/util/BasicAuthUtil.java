package net.devopssolutions.microservice.util;

import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.codec.Base64;

import java.nio.charset.StandardCharsets;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public final class BasicAuthUtil {

    private BasicAuthUtil() {
    }

    public static HttpHeaders getBasicAuthHeaders(String username, String password) {
        return getBasicAuthHeaders(new HttpHeaders(), username, password);
    }

    public static HttpHeaders getBasicAuthHeaders(HttpHeaders headers, String username, String password) {
        String auth = username + ":" + password;
        byte[] bytes = Base64.encode(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + new String(bytes, StandardCharsets.UTF_8);
        headers.set(AUTHORIZATION, authHeader);
        return headers;
    }

}
