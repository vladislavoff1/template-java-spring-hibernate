package com.example.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by vlad on 27/12/2016.
 */

@Entity
public class Match {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private User player1;

    @ManyToOne
    private User player2;

    @Enumerated(EnumType.ORDINAL)
    private MatchState state;

    private int seconds;
    private int result1 = 0;
    private int result2 = 0;

    private Date startAt;
    private Date endTime;
    private Date createdAt;


    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    public int getId() {
        return id;
    }

    public User getPlayer1() {
        return player1;
    }

    public void setPlayer1(User player1) {
        this.player1 = player1;
    }

    public User getPlayer2() {
        return player2;
    }

    public void setPlayer2(User player2) {
        this.player2 = player2;
    }

    public MatchState getState() {
        return state;
    }

    public void setState(MatchState state) {
        this.state = state;

        switch (state) {
            case ACCEPTED:

                Calendar calendar = Calendar.getInstance();
                this.startAt = calendar.getTime();

                calendar.add(Calendar.SECOND, seconds);
                this.endTime = calendar.getTime();

                break;
        }
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getSeconds() {
        return seconds;
    }

    public Date getStartAt() {
        return startAt;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public int getResult1() {
        return result1;
    }

    public void setResult1(int result1) {
        this.result1 = result1;
    }

    public int getResult2() {
        return result2;
    }

    public void setResult2(int result2) {
        this.result2 = result2;
    }
}
