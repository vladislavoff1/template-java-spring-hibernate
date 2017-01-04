package com.example.service;

import facebook4j.*;
import facebook4j.auth.AccessToken;
import facebook4j.conf.Configuration;
import facebook4j.conf.ConfigurationBuilder;
import org.springframework.stereotype.Service;

import java.net.URL;

/**
 * Created by vlad on 02/01/2017.
 */

@Service
public class FacebookServiceImpl implements FacebookService {

    private static Configuration createConfiguration() {
        ConfigurationBuilder confBuilder = new ConfigurationBuilder();

        confBuilder.setDebugEnabled(true);
        confBuilder.setOAuthAppId("955081524625791");
        confBuilder.setOAuthAppSecret("2b896d7634be547a659e53741cf445ee");
        confBuilder.setUseSSL(true);
        confBuilder.setJSONStoreEnabled(true);
        return confBuilder.build();
    }

    private static Facebook connectFacebook(String token) {
        Configuration configuration = createConfiguration();
        FacebookFactory facebookFactory = new FacebookFactory(configuration);
        Facebook facebookClient = facebookFactory.getInstance();
        AccessToken accessToken = new AccessToken(token);
        facebookClient.setOAuthAccessToken(accessToken);

        return facebookClient;
    }

    private static Facebook connectFacebook() {
        Configuration configuration = createConfiguration();
        FacebookFactory facebookFactory = new FacebookFactory(configuration);
        Facebook facebookClient = facebookFactory.getInstance();

        return facebookClient;
    }

    @Override
    public User getUser(String token) {
        Facebook fb = connectFacebook(token);

        try {
            return fb.getMe();
        } catch (FacebookException e) {
            e.printStackTrace();
        }


        return null;
    }


    public URL getPictureURL(String token, String userId) {

        Facebook fb = connectFacebook(token);

        try {
            return fb.getPictureURL(userId);
        } catch (FacebookException e) {
            e.printStackTrace();
        }

        return null;
    }


    public ResponseList<Friend> getFriends(String token) {

        Facebook fb = connectFacebook(token);

        try {
            return fb.getFriends();
        } catch (FacebookException e) {
            e.printStackTrace();
        }

        return null;
    }

}
