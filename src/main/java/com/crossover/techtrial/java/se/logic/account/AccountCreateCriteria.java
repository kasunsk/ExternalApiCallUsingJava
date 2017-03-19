package com.crossover.techtrial.java.se.logic.account;

import com.crossover.techtrial.java.se.dto.account.AccountRequest;
import com.crossover.techtrial.java.se.model.user.User;

/**
 * Created by kasun on 3/19/17.
 */
public class AccountCreateCriteria {

    private AccountRequest accountRequest;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AccountRequest getAccountRequest() {
        return accountRequest;
    }

    public void setAccountRequest(AccountRequest accountRequest) {
        this.accountRequest = accountRequest;
    }
}
