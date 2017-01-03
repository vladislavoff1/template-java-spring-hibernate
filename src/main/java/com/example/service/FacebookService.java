package com.example.service;

import facebook4j.Friend;
import facebook4j.ResponseList;
import facebook4j.User;

import java.net.URL;

/**
 * Created by vlad on 02/01/2017.
 */
public interface FacebookService {

    User getUser(String token);

    URL getPictureURL(String token, String userId);

    ResponseList<Friend> getFriends(String token);

}
