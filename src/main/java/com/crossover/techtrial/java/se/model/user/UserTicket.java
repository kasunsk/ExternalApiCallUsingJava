package com.crossover.techtrial.java.se.model.user;

import com.crossover.techtrial.java.se.common.dto.Currency;
import com.crossover.techtrial.java.se.common.dto.UserTicketStatus;
import com.crossover.techtrial.java.se.model.AbstractTrackableEntity;

import javax.persistence.*;

@Entity
@Table(name="USER_TICKET")
public class UserTicket extends AbstractTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "OFFER_ID", nullable = false)
    private Long offerId;

    @Column(name = "ORIGIN", nullable = false)
    private String origin;

    @Column(name = "DESTINATION", nullable = false)
    private String destination;

    @Column(name = "PRICE", nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCY", nullable = false)
    private Currency currency;

    @Column(name = "TICKETS_AMOUNT", nullable = false)
    private Integer ticketsAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private UserTicketStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Integer getTicketsAmount() {
        return ticketsAmount;
    }

    public void setTicketsAmount(Integer ticketsAmount) {
        this.ticketsAmount = ticketsAmount;
    }

    public UserTicketStatus getStatus() {
        return status;
    }

    public void setStatus(UserTicketStatus status) {
        this.status = status;
    }
}
