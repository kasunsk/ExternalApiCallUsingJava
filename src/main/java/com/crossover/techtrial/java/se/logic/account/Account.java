package com.crossover.techtrial.java.se.logic.account;


import com.crossover.techtrial.java.se.dto.airline.Price;

import java.io.Serializable;

/**
 * Created by kasun on 3/19/17.
 */
public class Account implements Serializable {

    private String id;
    private Price balance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Price getBalance() {
        return balance;
    }

    public void setBalance(Price balance) {
        this.balance = balance;
    }
}
