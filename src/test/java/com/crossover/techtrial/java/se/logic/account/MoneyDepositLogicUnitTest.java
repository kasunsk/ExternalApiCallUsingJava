package com.crossover.techtrial.java.se.logic.account;


import com.crossover.techtrial.java.se.common.dto.Currency;
import com.crossover.techtrial.java.se.common.dto.Price;
import com.crossover.techtrial.java.se.common.dto.ServiceResponse;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.dao.account.AccountHibernateDao;
import com.crossover.techtrial.java.se.dto.account.DepositRequest;
import com.crossover.techtrial.java.se.dto.account.MoneyTransferRequest;
import com.crossover.techtrial.java.se.model.account.BankAccount;
import com.crossover.techtrial.java.se.service.account.AccountService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

public class MoneyDepositLogicUnitTest {

    @InjectMocks
    MoneyDepositLogic logic = new MoneyDepositLogic();

    @Mock
    AccountHibernateDao accountHibernateDao;

    @Mock
    AccountService accountService;

    @BeforeMethod(alwaysRun = true)
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void validateDepositRequestTest() {
        logic.invoke(null);
    }
//
//    @Test(expectedExceptions = ServiceRuntimeException.class)
//    public void validateAccountIdNullTest() {
//        logic.invoke(new DepositRequest());
//    }
//
//    @Test(expectedExceptions = ServiceRuntimeException.class)
//    public void validatePriceNullTest() {
//
//        MoneyTransferRequest request = new MoneyTransferRequest();
//        request.setAccountId("test");
//        logic.invoke(request);
//    }
//
//    @Test(expectedExceptions = ServiceRuntimeException.class)
//    public void validatePriceCurrencyNullTest() {
//
//        DepositRequest request = new DepositRequest();
//        request.setAccountId("test");
//        request.setPrice(new Price());
//        logic.invoke(request);
//    }
//
//    @Test(expectedExceptions = ServiceRuntimeException.class)
//    public void validatePriceAmountNullTest() {
//
//        DepositRequest request = new DepositRequest();
//        request.setAccountId("test");
//        Price price = new Price();
//        price.setCurrency(Currency.AUD);
//        request.setPrice(price);
//        logic.invoke(request);
//    }
//
//    @Test(expectedExceptions = ServiceRuntimeException.class)
//    public void getNewBalanceFailTest() {
//
//        DepositRequest request = getDepositRequest();
//        when(accountHibernateDao.loadAccountById(12L)).thenReturn(null);
//        logic.invoke(request);
//    }
//
//    @SuppressWarnings("unchecked")
//    @Test
//    public void invokeTest() {
//        BankAccount bankAccount = getBankAccount();
//
//        when(accountHibernateDao.loadAccountById(12L)).thenReturn(bankAccount);
//
//        DepositRequest depositRequest = getDepositRequest();
//        BankAccount account = logic.invoke(depositRequest);
//        verify(accountService, times(0)).exchangeCurrency(any());
//        assertEquals(account, bankAccount);
//    }
//
//    @Test
//    public void invokeMoneyExchangeTest() {
//
//        BankAccount bankAccount = getBankAccount();
//        //bankAccount.setCurrency(Currency.USD);
//        DepositRequest depositRequest = getDepositRequest();
//
//        when(accountHibernateDao.loadAccountById(12L)).thenReturn(bankAccount);
//
//        ServiceResponse<Price> response = getDifferenceCurrencyPriceResponse();
//        when(accountService.exchangeCurrency(any())).thenReturn(response);
//
//        BankAccount responseAccount = logic.invoke(depositRequest);
//        verify(accountService, times(1)).exchangeCurrency(any());
//        assertEquals(responseAccount, bankAccount);
//    }
//
//    private ServiceResponse<Price> getDifferenceCurrencyPriceResponse() {
//        Price price = new Price();
//        price.setCurrency(Currency.USD);
//        price.setPrice(150D);
//        ServiceResponse<Price> response = new ServiceResponse<>();
//        response.setPayload(price);
//        return response;
//    }
//
//    private BankAccount getBankAccount() {
//        BankAccount bankAccount = new BankAccount();
//        //bankAccount.setCurrency(Currency.AUD);
//       // bankAccount.setAvailableAmount(1000D);
//        return bankAccount;
//    }
//
//    private DepositRequest getDepositRequest() {
//        DepositRequest request = new DepositRequest();
//        request.setAccountId("12");
//        Price price = new Price();
//        price.setCurrency(Currency.AUD);
//        price.setPrice(200D);
//        request.setPrice(price);
//        return request;
//    }
}
