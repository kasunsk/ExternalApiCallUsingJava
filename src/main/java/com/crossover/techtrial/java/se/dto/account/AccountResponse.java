package com.crossover.techtrial.java.se.dto.account;

import com.crossover.techtrial.java.se.common.dto.Price;

public class AccountResponse {

    private String accountId;

    private Price availableAmount;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Price getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(Price availableAmount) {
        this.availableAmount = availableAmount;
    }
}
