package com.crossover.techtrial.java.se.common.dto;

import com.crossover.techtrial.java.se.dto.airline.Price;

public class CurrencyExchangeRequest {

    private Price monetaryAmount;
    private Currency targetCurrency;

    public Price getMonetaryAmount() {
        return monetaryAmount;
    }

    public void setMonetaryAmount(Price monetaryAmount) {
        this.monetaryAmount = monetaryAmount;
    }

    public Currency getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(Currency targetCurrency) {
        this.targetCurrency = targetCurrency;
    }
}
