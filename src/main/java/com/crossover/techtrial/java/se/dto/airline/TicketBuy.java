package com.crossover.techtrial.java.se.dto.airline;

/**
 * Created by kasun on 3/2/17.
 */
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
