package com.crossover.techtrial.java.se.logic.account;

import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.configuration.ApplicationProperties;
import com.crossover.techtrial.java.se.dao.account.AccountHibernateDao;
import com.crossover.techtrial.java.se.dto.account.Account;
import com.crossover.techtrial.java.se.model.account.BankAccount;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

public class AllAccountsLoadingLogicUnitTest {

    @InjectMocks
    AllAccountsLoadingLogic logic = new AllAccountsLoadingLogic();

    @Mock
    AccountHibernateDao accountDao;

    @Mock
    ApplicationProperties applicationProperties;

    @Mock
    RestTemplate restTemplate;

    @BeforeMethod(alwaysRun=true)
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void validateInputNullTest() {
        logic.invoke(null);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void validateInputEmptyTest() {
        logic.invoke("");
    }

    @Test
    public void invokeTest() {

        List<BankAccount> accounts = new ArrayList<>();
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber("aaaa");
        accounts.add(bankAccount);

        Account accountOne = new Account();
        accountOne.setId("aaaa");
        Account accountTwo = new Account();
        accountTwo.setId("asasas");
        Account[] accountArray = new Account[] {accountOne, accountTwo};
        ResponseEntity<Account[]> responseEntity = new ResponseEntity<>(accountArray, HttpStatus.OK);
        when(applicationProperties.getBaseAPIUrl()).thenReturn("baseUrl");
        when(applicationProperties.getApplicantId()).thenReturn("aaaa");
        when(restTemplate.getForEntity(anyString(), eq(Account[].class))).thenReturn(responseEntity);
        when(accountDao.loadAccountByApplicantId("5")).thenReturn(accounts);
        List<Account> result = logic.invoke("5");
        assertNotNull(result);
        assertEquals(result.size(), 1);
    }

}
