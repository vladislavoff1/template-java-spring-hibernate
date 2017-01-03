package com.example.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by vlad on 27/12/2016.
 */

@Entity
public class MatchActions {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Match match;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.ORDINAL)
    private MathActionsState state;

    private int coin;
    private int step;


    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }


}
