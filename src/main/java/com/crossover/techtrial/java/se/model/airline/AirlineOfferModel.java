package com.crossover.techtrial.java.se.model.airline;

import com.crossover.techtrial.java.se.common.dto.Currency;
import com.crossover.techtrial.java.se.dto.airline.AirlineOffer;
import com.crossover.techtrial.java.se.model.AbstractTrackableEntity;

import javax.persistence.*;

@Entity
@Table(name = "AIRLINE_OFFER")
public class AirlineOfferModel extends AbstractTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OFFER_ID", nullable = false)
    private Long offerId;

    @Column(name = "PRICE", nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCY", nullable = false)
    private Currency currency;

    @Column(name = "ORIGIN", nullable = false)
    private String origin;

    @Column(name = "DESTINATION", nullable = false)
    private String destination;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private AirlineOffer.AirlineOfferStatus status;

    @Column(name = "AVAILABLE_INV", nullable = false)
    private Integer availbaleInventory;

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

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public AirlineOffer.AirlineOfferStatus getStatus() {
        return status;
    }

    public void setStatus(AirlineOffer.AirlineOfferStatus status) {
        this.status = status;
    }

    public Integer getAvailbaleInventory() {
        return availbaleInventory;
    }

    public void setAvailbaleInventory(Integer availbaleInventory) {
        this.availbaleInventory = availbaleInventory;
    }
}
