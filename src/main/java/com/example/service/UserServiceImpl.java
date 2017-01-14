package com.example.service;

import com.example.model.User;
import com.example.model.User_;
import facebook4j.Friend;
import facebook4j.ResponseList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vlad on 27/12/2016.
 */

@Service
public class UserServiceImpl implements UserService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private FacebookService facebookService;

    private User getCurrentUser() {
        String uid = SecurityContextHolder.getContext().getAuthentication().getName();

        return get(uid);
    }

    @Transactional
    public User addUser(User user) {
        em.persist(user);
        em.flush();
        return user;
    }

    @Transactional
    public List<User> getUsersList() {
        CriteriaQuery<User> c = em.getCriteriaBuilder().createQuery(User.class);
        c.from(User.class);

        return em.createQuery(c).getResultList();
    }

    @Override
    public User me() {
        return getCurrentUser();
    }

    @Override
    public User get(String uid) {

        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<User> criteria = builder.createQuery(User.class);

        Root<User> userRoot = criteria.from(User.class);
        criteria.select(userRoot);

        criteria.where(
                builder.equal(userRoot.get(User_.uid), uid)
        );

        List<User> users = em.createQuery(criteria).getResultList();

        if (users.size() != 0) {
            return users.get(0);
        }

        return null;
    }

    @Override
    @Transactional
    public User create(String uid, String name) {
        return create(uid, name, null);
    }

    @Override
    @Transactional
    public User create(String uid, String name, String avatar) {
        User user = new User();
        user.setUid(uid);
        user.setName(name);
        user.setAvatar(avatar);

        return addUser(user);
    }

    @Override
    public List<User> myFriends() {
        User user = getCurrentUser();
        if (user == null) {
            return null;
        }

        String token = user.getFbToken();
        if (token == null) {
            return null;
        }

        ResponseList<Friend> friends = facebookService.getFriends(token);

        List<User> friendsList = new ArrayList<>();


        for (Friend friend : friends) {
            String id = friend.getId();
            Logger.getGlobal().log(Level.WARNING, "friend " + id);
            friendsList.add(get(id));
        }

        return friendsList;
    }

}
