package com.crossover.techtrial.java.se.dto.airline;

import com.crossover.techtrial.java.se.model.airline.Route;

import java.io.Serializable;

public class GammaAirlineOffer implements Serializable {

    private Route route;
    private Price price;

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }
}
