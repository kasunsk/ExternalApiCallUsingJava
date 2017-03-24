package com.crossover.techtrial.java.se.logic.account;


import com.crossover.techtrial.java.se.common.dto.Currency;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.configuration.ApplicationProperties;
import com.crossover.techtrial.java.se.dao.account.AccountHibernateDao;
import com.crossover.techtrial.java.se.dto.account.Account;
import com.crossover.techtrial.java.se.dto.account.MoneyTransferRequest;
import com.crossover.techtrial.java.se.dto.airline.Price;
import com.crossover.techtrial.java.se.model.account.BankAccount;
import com.crossover.techtrial.java.se.service.account.AccountService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;


public class MoneyDepositLogicUnitTest {

    @InjectMocks
    MoneyDepositLogic logic = new MoneyDepositLogic();

    @Mock
    AccountService accountService;

    @Mock
    ApplicationProperties applicationProperties;

    @Mock
    RestTemplate restTemplate;

    @BeforeMethod(alwaysRun = true)
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void validateDepositRequestTest() {
        logic.invoke(null);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void validateAccountIdNullTest() {
        logic.invoke(new MoneyTransferRequest());
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void validatePriceNullTest() {

        MoneyTransferRequest request = getMoneyTransferRequest();
        logic.invoke(request);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void validatePriceCurrencyNullTest() {

        MoneyTransferRequest request = getMoneyTransferRequest();
        request.setMonetaryAmount(new Price());
        logic.invoke(request);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void validatePriceAmountNullTest() {

        MoneyTransferRequest request = getMoneyTransferRequest();
        Price price = new Price();
        price.setCurrency(Currency.AUD);
        request.setMonetaryAmount(price);
        logic.invoke(request);
    }


    @Test
    public void invokeTest() {

        when(applicationProperties.getApplicantId()).thenReturn("aaaa");
        when(applicationProperties.getBaseAPIUrl()).thenReturn("www.crosover.com");
        Account account = new Account();
        ResponseEntity<Account> response = new ResponseEntity<>(account, HttpStatus.OK);
        when(restTemplate.postForEntity(anyString(), any(), eq(Account.class))).thenReturn(response);

        MoneyTransferRequest request = getMoneyTransferRequest();
        Price price = getPrice();
        request.setMonetaryAmount(price);
        Account result = logic.invoke(request);
        assertEquals(account, result);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void invokeFailBadRequestTest() {

        when(applicationProperties.getApplicantId()).thenReturn("aaaa");
        when(applicationProperties.getBaseAPIUrl()).thenReturn("www.crosover.com");
        when(restTemplate.postForEntity(anyString(), any(), eq(Account.class))).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        MoneyTransferRequest request = getMoneyTransferRequest();
        Price price = getPrice();
        request.setMonetaryAmount(price);
        logic.invoke(request);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void invokeFailNotFoundTest() {

        when(applicationProperties.getApplicantId()).thenReturn("aaaa");
        when(applicationProperties.getBaseAPIUrl()).thenReturn("www.crosover.com");
        when(restTemplate.postForEntity(anyString(), any(), eq(Account.class))).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        MoneyTransferRequest request = getMoneyTransferRequest();
        Price price = getPrice();
        request.setMonetaryAmount(price);
        logic.invoke(request);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void invokeFaiTest() {

        when(applicationProperties.getApplicantId()).thenReturn("aaaa");
        when(applicationProperties.getBaseAPIUrl()).thenReturn("www.crosover.com");
        when(restTemplate.postForEntity(anyString(), any(), eq(Account.class))).thenThrow(new HttpClientErrorException(HttpStatus.METHOD_NOT_ALLOWED));

        MoneyTransferRequest request = getMoneyTransferRequest();
        Price price = getPrice();
        request.setMonetaryAmount(price);
        logic.invoke(request);
    }

    private Price getPrice() {
        Price price = new Price();
        price.setCurrency(Currency.AUD);
        price.setAmount(100D);
        return price;
    }

    private MoneyTransferRequest getMoneyTransferRequest() {
        MoneyTransferRequest request = new MoneyTransferRequest();
        request.setAccountId("test");
        return request;
    }
}
