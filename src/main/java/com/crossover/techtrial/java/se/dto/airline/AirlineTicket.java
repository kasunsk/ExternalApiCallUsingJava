package com.crossover.techtrial.java.se.dto.airline;

/**
 * Created by kasun on 2/4/17.
 */
public class AirlineTicket {

    private Integer amount;
    private GammaAirlineOffer details;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public GammaAirlineOffer getDetails() {
        return details;
    }

    public void setDetails(GammaAirlineOffer details) {
        this.details = details;
    }
}
