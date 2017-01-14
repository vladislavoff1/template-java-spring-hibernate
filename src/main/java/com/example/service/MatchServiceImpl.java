package com.example.service;

import com.example.model.Match;
import com.example.model.MatchState;
import com.example.model.Match_;
import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * Created by vlad on 27/12/2016.
 */

@Service
public class MatchServiceImpl implements MatchService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserService userService;


    @Override
    @Transactional
    public List<Match> history() {
        User user = userService.me();

        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<Match> criteria = builder.createQuery(Match.class);

        Root<Match> matchRoot = criteria.from(Match.class);
        criteria.select(matchRoot);

        criteria.where(
                builder.equal(matchRoot.get(Match_.state), MatchState.FINISHED),
                builder.or(
                        builder.equal(matchRoot.get(Match_.player1), user),
                        builder.equal(matchRoot.get(Match_.player2), user)
                )
        );

        return em.createQuery(criteria).getResultList();
    }

    @Override
    @Transactional
    public Match activeMatch() {
        User user = userService.me();

        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<Match> criteria = builder.createQuery(Match.class);

        Root<Match> matchRoot = criteria.from(Match.class);
        criteria.select(matchRoot);

        criteria.where(
                builder.or(
                        builder.equal(matchRoot.get(Match_.state), MatchState.CREATED),
                        builder.equal(matchRoot.get(Match_.state), MatchState.ACCEPTED)
                ),
                builder.or(
                        builder.equal(matchRoot.get(Match_.player1), user),
                        builder.equal(matchRoot.get(Match_.player2), user)
                )
        );

        List<Match> matches = em.createQuery(criteria).getResultList();

        Match match = null;
        if (matches.size() != 0) {
            match = matches.get(0);
        }

        if (match != null) {
            if (match.getState() == MatchState.ACCEPTED) {
                if (new Date().after(match.getEndTime())) {
                    match.setState(MatchState.FINISHED);
                    em.flush();
                }
            }
        }

        return match;
    }

    @Override
    @Transactional
    public Match create(User player2, int seconds) {
        User player1 = userService.me();

        Match match = new Match();

        match.setPlayer1(player1);
        match.setPlayer2(player2);

        match.setState(MatchState.CREATED);
        match.setSeconds(seconds);

        em.persist(match);
        em.flush();

        return match;
    }

    @Override
    @Transactional
    public Match cancel(int matchId) {
        User user = userService.me();

        Match match = em.find(Match.class, matchId);

        if (user.getId() != match.getPlayer1().getId()) {
            return null;
        }

        if (match.getState() == MatchState.CREATED) {
            match.setState(MatchState.CANCELED);
            em.flush();
        }

        return match;
    }

    @Override
    @Transactional
    public Match accept(int matchId) {
        User user = userService.me();

        Match match = em.find(Match.class, matchId);

        if (user.getId() != match.getPlayer2().getId()) {
            return null;
        }

        if (match.getState() == MatchState.CREATED) {
            match.setState(MatchState.ACCEPTED);
            em.flush();
        }

        return match;
    }

    @Override
    @Transactional
    public Match reject(int matchId) {
        User user = userService.me();

        Match match = em.find(Match.class, matchId);

        if (user.getId() != match.getPlayer2().getId()) {
            return null;
        }

        if (match.getState() == MatchState.CREATED) {
            match.setState(MatchState.REJECTED);
            em.flush();
        }

        return match;
    }

    @Override
    @Transactional
    public Match setResult(int matchId, int result) {
        User user = userService.me();

        Match match = em.find(Match.class, matchId);

        int id = user.getId();
        int p1 = match.getPlayer1().getId();
        int p2 = match.getPlayer2().getId();

        if (id != p1 && id != p2) {
            return null;
        }

        if (match.getState() != MatchState.ACCEPTED) {
            return match;
        }

        Date endTime = match.getEndTime();

        if (new Date().after(endTime)) {
            match.setState(MatchState.FINISHED);
            em.flush();
            return match;
        }

        if (id == p1) {
            match.setResult1(result);
        }

        if (id == p2) {
            match.setResult2(result);
        }

        em.flush();

        return match;
    }
}
