package com.crossover.techtrial.java.se.dto.airline;

import com.crossover.techtrial.java.se.model.airline.Route;

public class TicketBuyingRequest {

    private String accountId;
    private Integer ticketAmount;
    private Route airlineRout;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Integer getTicketAmount() {
        return ticketAmount;
    }

    public void setTicketAmount(Integer ticketAmount) {
        this.ticketAmount = ticketAmount;
    }

    public Route getAirlineRout() {
        return airlineRout;
    }

    public void setAirlineRout(Route airlineRout) {
        this.airlineRout = airlineRout;
    }
}
