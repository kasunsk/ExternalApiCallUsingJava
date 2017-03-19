package com.crossover.techtrial.java.se.dto.account;


import com.crossover.techtrial.java.se.dto.airline.Price;

public class MoneyTransferRequest {

    private String accountId;
    private Price monetaryAmount;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Price getMonetaryAmount() {
        return monetaryAmount;
    }

    public void setMonetaryAmount(Price monetaryAmount) {
        this.monetaryAmount = monetaryAmount;
    }
}
