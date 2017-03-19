package com.crossover.techtrial.java.se.model.account;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.crossover.techtrial.java.se.common.dto.Currency;
import com.crossover.techtrial.java.se.model.AbstractTrackableEntity;
import com.crossover.techtrial.java.se.model.user.User;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

/**
 * Created by kasun on 2/4/17.
 */
@Entity
@Table(name="BANK_ACCOUNT")
public class BankAccount extends AbstractTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long accountId;

    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "USER_ID")
    @ManyToOne(cascade= CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCY", nullable = false)
    private Currency currency;

    @Column(name = "AVAILABLE_AMOUNT", nullable = false)
    private Double availableAmount;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(Double availableAmount) {
        this.availableAmount = availableAmount;
    }
}
