package com.example.controller;

import com.example.model.User;
import com.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by vlad on 02/01/2017.
 */

@Controller
@RequestMapping(value = "auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/login", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public User getUser(@RequestParam("token") String token) {

        return authService.login(token);
    }

}