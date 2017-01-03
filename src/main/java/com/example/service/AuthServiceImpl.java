package com.example.service;

import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    private UserService userService;

    @Transactional
    public User login(String token) {
        facebook4j.User fbUser = facebookService.getUser(token);

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

        return user;
    }

}
