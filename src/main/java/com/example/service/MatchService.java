package com.example.service;

import com.example.model.Match;
import com.example.model.User;

import java.util.List;

/**
 * Created by vlad on 27/12/2016.
 */
public interface MatchService {

    List<Match> history();
    Match activeMatch();

    // creator methods
    Match create(User player2, int seconds);
    Match cancel(int matchId);

    // enemy methods
    Match accept(int matchId);
    Match reject(int matchId);

    // common
    Match setResult(int matchId, int result);

}
