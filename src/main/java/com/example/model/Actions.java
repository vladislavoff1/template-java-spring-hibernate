package com.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by vlad on 27/12/2016.
 */
@Entity
public class Actions {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private User user;
    private int coin;
    private int time;

}
