package com.crossover.techtrial.java.se.dto.user;

/**
 * Created by kasun on 3/20/17.
 */
public class UserTicketSearchCriteria {

    private String email;
    private String origin;
    private String destination;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
