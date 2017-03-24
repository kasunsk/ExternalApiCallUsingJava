package com.crossover.techtrial.java.se.controller;


import com.crossover.techtrial.java.se.common.dto.*;
import com.crossover.techtrial.java.se.dto.account.Account;
import com.crossover.techtrial.java.se.dto.account.AccountRequest;
import com.crossover.techtrial.java.se.dto.account.MoneyTransferRequest;
import com.crossover.techtrial.java.se.dto.airline.Price;
import com.crossover.techtrial.java.se.model.user.User;
import com.crossover.techtrial.java.se.service.account.AccountService;
import com.crossover.techtrial.java.se.service.user.UserService;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

public class AccountControllerUnitTest {

    @InjectMocks
    AccountController controller = new AccountController();

    @Mock
    AccountService accountService;

    @Mock
    UserService userService;

    @BeforeMethod(alwaysRun = true)
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void createAccountTest() {

        Account account = new Account();
        ServiceResponse<Account> accountResponse = buildAccountServiceResponse(account);
        when(accountService.createAccount(org.mockito.Matchers.<ServiceRequest>any())).thenReturn(accountResponse);
        ServiceResponse<User> userResponse = new ServiceResponse<>();
        userResponse.setPayload(new User());
        when(userService.loadUserById(org.mockito.Matchers.<ServiceRequest>any())).thenReturn(userResponse);
        AccountRequest request = new AccountRequest();
        request.setCurrency(Currency.AUD);
        assertEquals(controller.createAccount("23", request), account);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void depositsTest() {

        Account account = new Account();
        ServiceResponse<Account> accountResponse = buildAccountServiceResponse(account);
        when(accountService.deposit(org.mockito.Matchers.<ServiceRequest>any())).thenReturn(accountResponse);
        MoneyTransferRequest request = new MoneyTransferRequest();
        request.setAccountId("A12S");
        request.setMonetaryAmount(new Price());
        assertEquals(controller.deposits("23", request), account);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void viewAllAccountsTest() {

        List<Account> accounts = new ArrayList<>();
        Account account = new Account();
        ServiceResponse<List<Account>> accountResponse = new ServiceResponse<>();
        accounts.add(account);
        accountResponse.setPayload(accounts);
        when(accountService.loadAllAccounts(Matchers.<ServiceRequest>any())).thenReturn(accountResponse);
        assertEquals(controller.viewAllAccounts("23"), accounts);
    }


    private ServiceResponse<Account> buildAccountServiceResponse(Account account) {
        ServiceResponse<Account> accountResponse = new ServiceResponse<>();
        accountResponse.setPayload(account);
        return accountResponse;
    }

}
