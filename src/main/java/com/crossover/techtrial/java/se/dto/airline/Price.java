package com.crossover.techtrial.java.se.dto.airline;

import com.crossover.techtrial.java.se.common.dto.Currency;

import java.io.Serializable;

public class Price implements Serializable {

    private Double amount;
    private Currency currency;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
