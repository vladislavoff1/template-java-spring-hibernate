package com.example.service;

import com.example.model.User;

/**
 * Created by vlad on 02/01/2017.
 */

public interface AuthService {

    User login(String token);

}
