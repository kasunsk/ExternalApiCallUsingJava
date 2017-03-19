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

    @Column(name = "ACCOUNT_NUMBER", nullable = false)
    private String accountNumber;


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

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
