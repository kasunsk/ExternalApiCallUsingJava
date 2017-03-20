package com.crossover.techtrial.java.se.dto.airline;

public class TicketBuy {

    private TicketBuyingRequest ticketBuyingRequest;
    private String userId;

    public TicketBuyingRequest getTicketBuyingRequest() {
        return ticketBuyingRequest;
    }

    public void setTicketBuyingRequest(TicketBuyingRequest ticketBuyingRequest) {
        this.ticketBuyingRequest = ticketBuyingRequest;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
