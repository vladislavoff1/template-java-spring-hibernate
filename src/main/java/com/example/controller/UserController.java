package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", produces = "application/json")
    @ResponseBody
    public List<User> usersList() {

        return userService.getUsersList();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public List<User> addUser(@ModelAttribute("User") User User) {

        userService.addUser(User);

        return userService.getUsersList();
    }
}
