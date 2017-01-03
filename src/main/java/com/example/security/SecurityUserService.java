package com.example.security;

import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vlad on 03/01/2017.
 */
public class SecurityUserService implements UserDetailsService {

    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
    private final static HashMap<String, User> userMap = new HashMap<>();

    @Override
    public final User loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userMap.get(username);
        if (user == null) {
            String keys = Arrays.toString(userMap.keySet().toArray());
            throw new UsernameNotFoundException("User " + username + " not found. Founded: " + keys);
        }
        detailsChecker.check(user);
        return user;
    }

    public void addUser(User user) {
        String keys = Arrays.toString(userMap.keySet().toArray());
        Logger.getGlobal().log(Level.WARNING, "addUser " + user.getUsername() + ". " + "Founded: " + keys);

        userMap.put(user.getUsername(), user);

        keys = Arrays.toString(userMap.keySet().toArray());
        Logger.getGlobal().log(Level.WARNING, "Founded: " + keys);
    }
}
