package com.crossover.techtrial.java.se.controller;

import com.crossover.techtrial.java.se.common.dto.Currency;
import com.crossover.techtrial.java.se.common.dto.ServiceRequest;
import com.crossover.techtrial.java.se.dto.account.AccountRequest;
import com.crossover.techtrial.java.se.dto.account.MoneyTransferRequest;
import com.crossover.techtrial.java.se.dto.account.Account;
import com.crossover.techtrial.java.se.dto.account.AccountCreateCriteria;
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

    @RequestMapping(value = "/{applicantId}/paypallets/account/deposit", method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Account deposits(@PathVariable("applicantId") String applicantId, @RequestBody MoneyTransferRequest depositRequest) {

        validateUser(applicantId);
        return accountService.deposit(new ServiceRequest<>(depositRequest)).getPayload();
    }


    @RequestMapping(value = "/{userId}/paypallets/account/all", method = RequestMethod.GET)
    @ResponseBody
    public List<Account> viewAllAccounts(@PathVariable("userId") String userId) {

        return accountService.loadAllAccounts(new ServiceRequest<>(userId)).getPayload();
    }


    @RequestMapping(value = "/account/availableCurrency", method = RequestMethod.GET)
    @ResponseBody
    public Set<Currency> availableCurrency() {
        return EnumSet.allOf(Currency.class);
    }

    private void validateUser(String applicantId) {

        userService.authenticateUser(new ServiceRequest<>(applicantId));
    }

}
