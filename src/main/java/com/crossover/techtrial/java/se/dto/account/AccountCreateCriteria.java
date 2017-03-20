package com.crossover.techtrial.java.se.dto.account;


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
