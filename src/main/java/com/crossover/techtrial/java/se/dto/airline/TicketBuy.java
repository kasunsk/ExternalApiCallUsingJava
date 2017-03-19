package com.crossover.techtrial.java.se.dto.airline;

/**
 * Created by kasun on 3/2/17.
 */
public class TicketBuy {

    private TicketBuyingRequest ticketBuyingRequest;

    private String applicantId;

    public TicketBuyingRequest getTicketBuyingRequest() {
        return ticketBuyingRequest;
    }

    public void setTicketBuyingRequest(TicketBuyingRequest ticketBuyingRequest) {
        this.ticketBuyingRequest = ticketBuyingRequest;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }
}
