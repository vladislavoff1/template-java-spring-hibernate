package com.example.service;

import com.example.model.User;
import com.example.security.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vlad on 02/01/2017.
 */
@Service
public class AuthServiceImpl implements AuthService {


    @PersistenceContext
    private EntityManager em;

    @Autowired
    private FacebookService facebookService;

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    private UserService userService;

    @Transactional
    public User login(String token) {

        Logger.getGlobal().log(Level.WARNING, "login ");

        String extendedToken = facebookService.extendTokenExpiration(token);

        facebook4j.User fbUser = facebookService.getUser(extendedToken);

        // TODO: bad token exception
        if (fbUser == null) {
            return null;
        }

        String uid = fbUser.getId();

        User user = userService.get(uid);

        if (user == null) {
            String avatar = null;

            if (fbUser.getPicture() != null) {
                avatar = fbUser.getPicture().getURL().toString();
            }

            user = userService.create(
                    uid,
                    fbUser.getName(),
                    avatar
            );
        }

        user.setFbToken(extendedToken);

        Map<String, String> headers = tokenAuthenticationService.addAuthentication(uid);
        user.setHeaders(headers);

        return user;
    }

}
