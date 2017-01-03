package com.example.controller;

import com.example.model.Match;
import com.example.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by vlad on 27/12/2016.
 */

@Controller
@RequestMapping(value = "matches")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @RequestMapping(value = "/", produces = "application/json")
    @ResponseBody
    public Match createMatch(
            @RequestParam("user_id") int userID,
            @RequestParam("player2_id") int player2ID) {

        return matchService.createMatch(userID, player2ID);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Match confirmMatch(
            @RequestParam("match_id") int matchID,
            @RequestParam("player2_id") int player2ID) {

        return matchService.confirmMatch(matchID, player2ID);
    }
}