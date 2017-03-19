package com.crossover.techtrial.java.se.model.user;

import com.crossover.techtrial.java.se.dto.user.UserRole;
import com.crossover.techtrial.java.se.model.AbstractTrackableEntity;
import com.crossover.techtrial.java.se.model.account.BankAccount;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * Created by kasun on 1/28/17.
 */
@Entity
@Table(name = "USER")
public class User extends AbstractTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_ROLE", nullable = false)
    private UserRole role;

    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountId")
    private List<BankAccount> userBankAccounts;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<BankAccount> getUserBankAccounts() {
        return userBankAccounts;
    }

    public void setUserBankAccounts(List<BankAccount> userBankAccounts) {
        this.userBankAccounts = userBankAccounts;
    }
}
