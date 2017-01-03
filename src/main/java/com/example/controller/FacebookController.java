package com.example.controller;

import com.example.service.FacebookService;
import facebook4j.Friend;
import facebook4j.ResponseList;
import facebook4j.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URL;

/**
 * Created by vlad on 02/01/2017.
 */
@Controller
@RequestMapping(value = "fb")
public class FacebookController {

    @Autowired
    private FacebookService facebookService;

    @RequestMapping(value = "/get_user", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public User getUser(@RequestParam("token") String token) {

        return facebookService.getUser(token);
    }

    @RequestMapping(value = "/get_picture", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public URL getPicture(@RequestParam("token") String token, @RequestParam("user_id") String userId) {

        return facebookService.getPictureURL(token, userId);
    }

    @RequestMapping(value = "/get_friends", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseList<Friend> getFriends(@RequestParam("token") String token) {

        return facebookService.getFriends(token);
    }

}