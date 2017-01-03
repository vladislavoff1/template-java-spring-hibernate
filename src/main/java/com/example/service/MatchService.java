package com.example.service;

import com.example.model.Match;

/**
 * Created by vlad on 27/12/2016.
 */
public interface MatchService {

    Match createMatch(int userID, int player2ID);

    Match confirmMatch(int userID, int matchID);

}
