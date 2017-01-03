package com.example.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by vlad on 27/12/2016.
 */

@Entity
public class Bank {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private User user;
    private int coin;

    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }
}
