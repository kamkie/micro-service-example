package net.devopssolutions.microservice.util;

import org.springframework.http.HttpHeaders;

import static java.nio.charset.Charset.forName;
import static org.apache.commons.codec.binary.Base64.encodeBase64String;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class BasicAuthUtil {

    public static HttpHeaders getBasicAuthHeaders(String username, String password) {
        return getBasicAuthHeaders(new HttpHeaders(), username, password);
    }

    public static HttpHeaders getBasicAuthHeaders(HttpHeaders headers, String username, String password) {
        String auth = username + ":" + password;
        String authHeader = "Basic " + encodeBase64String(auth.getBytes(forName("UTF-8")));
        headers.set(AUTHORIZATION, authHeader);
        return headers;
    }

}
