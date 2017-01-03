package com.example.model;

import javax.persistence.*;
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

    private Date startAt;
    private Date endAt;

    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
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
            case START:
                this.startAt = new Date();
                break;
            case END:
                this.endAt = new Date();
                break;
        }
    }
}
