package com.crossover.techtrial.java.se.controller;

import com.crossover.techtrial.java.se.common.dto.Currency;
import com.crossover.techtrial.java.se.common.dto.CurrencyExchangeRequest;
import com.crossover.techtrial.java.se.common.dto.Price;
import com.crossover.techtrial.java.se.common.dto.ServiceRequest;
import com.crossover.techtrial.java.se.dto.account.AccountRequest;
import com.crossover.techtrial.java.se.dto.account.DepositRequest;
import com.crossover.techtrial.java.se.logic.account.Account;
import com.crossover.techtrial.java.se.logic.account.AccountCreateCriteria;
import com.crossover.techtrial.java.se.model.account.BankAccount;
import com.crossover.techtrial.java.se.model.user.User;
import com.crossover.techtrial.java.se.service.account.AccountService;
import com.crossover.techtrial.java.se.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * This class provide http rest interface for Account Services
 */

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{userId}/paypallets/account", method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Account createAccount(@PathVariable("userId") String userId, @RequestBody AccountRequest accountRequest) {

        AccountCreateCriteria accountCreateCriteria = new AccountCreateCriteria();
        accountCreateCriteria.setUserId(userId);
        accountCreateCriteria.setAccountRequest(accountRequest);
        return accountService.createAccount(new ServiceRequest<>(accountCreateCriteria)).getPayload();
    }
//
//    @RequestMapping(value = "/{applicantId}/paypallets/account/deposit", method = RequestMethod.POST,
//            consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    @ResponseBody
//    public BankAccount deposits(@PathVariable("applicantId") String applicantId, @RequestBody DepositRequest depositRequest) {
//
//        validateUser(applicantId);
//        return accountService.deposit(new ServiceRequest<>(depositRequest)).getPayload();
//    }

    @RequestMapping(value = "/{applicantId}/paypallets/account/withdraw", method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public BankAccount withdraw(@PathVariable("applicantId") String applicantId, @RequestBody DepositRequest depositRequest) {

        validateUser(applicantId);
        return accountService.withdraw(new ServiceRequest<>(depositRequest)).getPayload();
    }

    @RequestMapping(value = "/{userId}/paypallets/account/all", method = RequestMethod.GET)
    @ResponseBody
    public List<Account> viewAllAccounts(@PathVariable("userId") String userId) {

        return accountService.loadAllAccounts(new ServiceRequest<>(userId)).getPayload();
    }

    @RequestMapping(value = "/{applicantId}/paypallets/account/remove/{accountId}", method = RequestMethod.GET)
    @ResponseBody
    public Boolean removeAccount(@PathVariable("applicantId") String applicantId, @PathVariable("accountId") String accountId) {

        accountService.removeAccount(new ServiceRequest<>(accountId));
        return Boolean.TRUE;
    }

    @RequestMapping(value = "/{applicantId}/moneyexchange/exchange", method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Price moneyExchange(@PathVariable("applicantId") String applicantId, @RequestBody CurrencyExchangeRequest exchangeRequest) {

        validateUser(applicantId);
        return accountService.exchangeCurrency(new ServiceRequest<>(exchangeRequest)).getPayload();
    }

    @RequestMapping(value = "/account/availableCurrency", method = RequestMethod.GET)
    @ResponseBody
    public Set<Currency> availableCurrency() {
        return EnumSet.allOf(Currency.class);
    }

    private void validateUser(String applicantId) {

        userService.authenticateUser(new ServiceRequest<>(applicantId));
    }

    private BankAccount buildAccountCreateRequest(String applicantId, AccountRequest accountRequest) {

        User user = userService.loadUserById(new ServiceRequest<>(applicantId)).getPayload();

        BankAccount bankAccount = new BankAccount();
        bankAccount.setUser(user);
      //  bankAccount.setCurrency(accountRequest.getCurrency());
        return bankAccount;
    }


}
