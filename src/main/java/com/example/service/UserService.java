package com.example.service;

import com.example.model.User;

import java.util.List;

/**
 * Created by vlad on 27/12/2016.
 */
public interface UserService {

    User addUser(User user);

    List<User> getUsersList();

    User me();

    User get(String uid);

    User create(String uid, String name);

    User create(String uid, String name, String avatar);
}
