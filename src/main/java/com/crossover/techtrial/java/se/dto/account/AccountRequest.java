package com.crossover.techtrial.java.se.dto.account;

import com.crossover.techtrial.java.se.common.dto.Currency;

public class AccountRequest {

    private Currency currency;

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
