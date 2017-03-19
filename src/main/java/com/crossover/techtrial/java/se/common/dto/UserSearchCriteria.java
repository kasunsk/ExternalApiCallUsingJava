package com.crossover.techtrial.java.se.common.dto;

/**
 * Created by kasun on 2/9/17.
 */
public class UserSearchCriteria {

    private String email;
    private String name;
    private Long userId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
