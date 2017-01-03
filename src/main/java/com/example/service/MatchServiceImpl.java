package com.example.service;

import com.example.model.Match;
import com.example.model.MatchState;
import com.example.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by vlad on 27/12/2016.
 */

@Service
public class MatchServiceImpl implements MatchService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Match createMatch(int userID, int player2ID) {

        User player1 = em.find(User.class, userID);
        User player2 = em.find(User.class, player2ID);

        Match match = new Match();
        match.setPlayer1(player1);
        match.setPlayer2(player2);
        match.setState(MatchState.WAIT);

        em.persist(match);

        return match;
    }

    @Transactional
    public Match confirmMatch(int userID, int matchID) {

        Match match = em.find(Match.class, matchID);

        if (match.getPlayer2().getId() != userID) {
            // user can't confirm that match
            return match;
        }

        match.setState(MatchState.START);

        em.persist(match);

        return match;
    }


}
