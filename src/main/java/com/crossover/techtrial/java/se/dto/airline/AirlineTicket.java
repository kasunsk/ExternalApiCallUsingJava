package com.crossover.techtrial.java.se.dto.airline;

/**
 * Created by kasun on 2/4/17.
 */
public class AirlineTicket {

    private Integer ticketsAmount;
    private AirlineOffer airlineOffer;

    public Integer getTicketsAmount() {
        return ticketsAmount;
    }

    public void setTicketsAmount(Integer ticketsAmount) {
        this.ticketsAmount = ticketsAmount;
    }

    public AirlineOffer getAirlineOffer() {
        return airlineOffer;
    }

    public void setAirlineOffer(AirlineOffer airlineOffer) {
        this.airlineOffer = airlineOffer;
    }
}
