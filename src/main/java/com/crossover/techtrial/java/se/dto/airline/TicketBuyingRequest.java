package com.crossover.techtrial.java.se.dto.airline;

import com.crossover.techtrial.java.se.model.airline.Route;

import java.io.Serializable;

public class TicketBuyingRequest implements Serializable{

    private String accountId;
    private Integer amount;
    private Route route;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
