package com.example.controller;

import com.example.model.Match;
import com.example.model.User;
import com.example.service.MatchService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by vlad on 27/12/2016.
 */

@Controller
@RequestMapping(value = "matches", produces = "application/json")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/history")
    @ResponseBody
    public List<Match> history() {
        return matchService.history();
    }

    @RequestMapping(value = "/active")
    @ResponseBody
    public Match active() {

        return matchService.activeMatch();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Match create(
            @RequestParam("player_id") String player2ID,
            @RequestParam("seconds") int seconds) {

        User player2 = userService.get(player2ID);

        return matchService.create(player2, seconds);
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    @ResponseBody
    public Match cancel(@RequestParam("match") int matchId) {
        return matchService.cancel(matchId);
    }

    @RequestMapping(value = "/accept", method = RequestMethod.POST)
    @ResponseBody
    public Match accept(@RequestParam("match") int matchId) {
        return matchService.accept(matchId);
    }

    @RequestMapping(value = "/reject", method = RequestMethod.POST)
    @ResponseBody
    public Match reject(@RequestParam("match") int matchId) {
        return matchService.reject(matchId);
    }

    @RequestMapping(value = "/result", method = RequestMethod.POST)
    @ResponseBody
    public Match setResult(@RequestParam("match") int matchId, @RequestParam("result") int result) {
        return matchService.setResult(matchId, result);
    }


}