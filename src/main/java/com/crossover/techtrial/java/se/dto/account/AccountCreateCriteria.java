package com.crossover.techtrial.java.se.dto.account;

import com.crossover.techtrial.java.se.dto.account.AccountRequest;
import com.crossover.techtrial.java.se.model.user.User;

/**
 * Created by kasun on 3/19/17.
 */
public class AccountCreateCriteria {

    private AccountRequest accountRequest;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public AccountRequest getAccountRequest() {
        return accountRequest;
    }

    public void setAccountRequest(AccountRequest accountRequest) {
        this.accountRequest = accountRequest;
    }
}
