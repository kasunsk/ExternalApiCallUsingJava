package com.crossover.techtrial.java.se.model.account;

import com.crossover.techtrial.java.se.common.dto.Currency;

public class Amount {

    private String amount;
    private Currency currency;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
