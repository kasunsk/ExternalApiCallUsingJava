package com.crossover.techtrial.java.se.logic.account;


import com.crossover.techtrial.java.se.common.dto.Currency;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.configuration.ApplicationProperties;
import com.crossover.techtrial.java.se.dao.account.AccountDao;
import com.crossover.techtrial.java.se.dao.user.UserDao;
import com.crossover.techtrial.java.se.dto.account.Account;
import com.crossover.techtrial.java.se.dto.account.AccountCreateCriteria;
import com.crossover.techtrial.java.se.dto.account.AccountRequest;
import com.crossover.techtrial.java.se.model.user.User;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class AccountCreateLogicUnitTest  {

    @InjectMocks
    AccountCreateLogic logic = new AccountCreateLogic();

    @Mock
    AccountDao accountDao;

    @Mock
    RestTemplate restTemplate;

    @Mock
    ApplicationProperties applicationProperties;

    @Mock
    UserDao userDao;


    @BeforeMethod(alwaysRun=true)
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void validateAccountNullTest() {
        logic.invoke(null);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void validateRequestNullTest() {
        logic.invoke(null);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void validateUserIdNullTest() {
        logic.invoke(new AccountCreateCriteria());
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void validateAccountRequestNullTest() {
        AccountCreateCriteria criteria = new AccountCreateCriteria();
        criteria.setUserId("5");
        logic.invoke(criteria);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void validateCurrencyNullTest() {

        AccountCreateCriteria criteria = new AccountCreateCriteria();
        criteria.setUserId("5");
        criteria.setAccountRequest(new AccountRequest());
        logic.invoke(criteria);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void invokeBadRequestFailTest() {

        AccountCreateCriteria criteria = new AccountCreateCriteria();
        criteria.setUserId("5");
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setCurrency(Currency.AED);
        criteria.setAccountRequest(accountRequest);

        ResponseEntity<Account> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        when(restTemplate.postForEntity(anyString(), any(), eq(Account.class))).thenReturn(response);
        logic.invoke(criteria);

    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void invokeNotFoundFailTest() {

        AccountCreateCriteria criteria = new AccountCreateCriteria();
        criteria.setUserId("5");
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setCurrency(Currency.AED);
        criteria.setAccountRequest(accountRequest);

        ResponseEntity<Account> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        when(restTemplate.postForEntity(anyString(), any(), eq(Account.class))).thenReturn(response);
        logic.invoke(criteria);

    }


    @SuppressWarnings("unchecked")
    @Test
    public void invokeTest() {

        AccountCreateCriteria criteria = new AccountCreateCriteria();
        criteria.setUserId("5");
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setCurrency(Currency.AED);
        criteria.setAccountRequest(accountRequest);

        Account account = new Account();
        ResponseEntity<Account> response = new ResponseEntity<>(account, HttpStatus.OK);
        when(restTemplate.postForEntity(anyString(), any(), eq(Account.class))).thenReturn(response);
        User user = new User();
        when(userDao.loadUserById("5")).thenReturn(user);
        assertEquals(logic.invoke(criteria), account);
        verify(accountDao, times(1)).createAccount(any());

    }

}
