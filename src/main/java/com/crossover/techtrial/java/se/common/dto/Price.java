package com.crossover.techtrial.java.se.common.dto;

import java.io.Serializable;

public class Price implements Serializable {

    private Double price;
    private Currency currency;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
