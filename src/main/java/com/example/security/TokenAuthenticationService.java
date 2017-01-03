package com.example.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vlad on 03/01/2017.
 */

@Service
public class TokenAuthenticationService {

    private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";

    private final TokenHandler tokenHandler;
    private final SecurityUserService userService;

    public TokenAuthenticationService(String secret, SecurityUserService userService) {
        this.userService = userService;
        tokenHandler = new TokenHandler(secret, userService);
    }

    public Map<String, String> addAuthentication(User user) {
        Logger.getGlobal().log(Level.WARNING, "addAuthentication");

        String token = tokenHandler.createTokenForUser(user);

        userService.addUser(user);

        Map<String, String> headers = new HashMap<>();
        headers.put(AUTH_HEADER_NAME, token);

        return headers;
    }

    public Map<String, String> addAuthentication(String uid) {
        return addAuthentication(new User(uid, uid, new ArrayList<>()));
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        final String token = request.getHeader(AUTH_HEADER_NAME);
        if (token != null) {
            final User user = tokenHandler.parseUserFromToken(token);
            if (user != null) {
                return new UserAuthentication(user);
            }
        }
        return null;
    }
}