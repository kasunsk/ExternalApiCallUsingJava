package com.crossover.techtrial.java.se.model.airline;

import java.io.Serializable;

/**
 * Created by kasun on 2/4/17.
 */
public class Route implements Serializable{

    private String from;
    private String to;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
