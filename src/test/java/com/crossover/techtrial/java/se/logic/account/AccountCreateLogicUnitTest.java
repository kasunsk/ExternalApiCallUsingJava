package com.crossover.techtrial.java.se.logic.account;


import com.crossover.techtrial.java.se.common.dto.Currency;
import com.crossover.techtrial.java.se.common.dto.Price;
import com.crossover.techtrial.java.se.common.dto.ServiceRequest;
import com.crossover.techtrial.java.se.common.dto.ServiceResponse;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.dao.account.AccountHibernateDao;
import com.crossover.techtrial.java.se.model.account.BankAccount;
import com.crossover.techtrial.java.se.model.user.User;
import com.crossover.techtrial.java.se.service.account.AccountService;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class AccountCreateLogicUnitTest  {

    @InjectMocks
    AccountCreateLogic logic = new AccountCreateLogic();

    @Mock
    AccountHibernateDao accountHibernateDao;

    @Mock
    AccountService accountService;


    @Mock
    Environment environment;

    @BeforeMethod(alwaysRun=true)
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void validateAccountNullTest() {
        logic.invoke(null);
    }
//
//    @Test(expectedExceptions = ServiceRuntimeException.class)
//    public void validateCurrencyNullTest() {
//        logic.invoke(new BankAccount());
//    }
//
//    @SuppressWarnings("unchecked")
//    @Test
//    public void invokeTest() {
//
//        BankAccount bankAccount = new BankAccount();
//       // bankAccount.setCurrency(Currency.AUD);
//        when(accountHibernateDao.createAccount(bankAccount)).thenReturn(bankAccount);
//        when(environment.getRequiredProperty("initial.deposit.amount")).thenReturn("1000");
//        when(environment.getRequiredProperty("initial.deposit.currency")).thenReturn("USD");
//
//        ServiceResponse<Price> response = new ServiceResponse<>();
//        Price price = new Price();
//        price.setCurrency(Currency.AUD);
//        price.setPrice(200D);
//        response.setPayload(price);
//        when(accountService.exchangeCurrency(Matchers.<ServiceRequest>any())).thenReturn(response);
//        BankAccount resultAccount = logic.invoke(bankAccount);
//        assertEquals(resultAccount, bankAccount);
//    }
//
//
//    @Test
//    public void invokeNotEmptyAvailableMoneyTest() {
//
//        BankAccount bankAccount = new BankAccount();
//        //bankAccount.setCurrency(Currency.USD);
//        //bankAccount.setAvailableAmount(2000D);
//        bankAccount.setUser(new User());
//        when(accountHibernateDao.createAccount(bankAccount)).thenReturn(bankAccount);
//        BankAccount resultAccount = logic.invoke(bankAccount);
//        assertEquals(resultAccount, bankAccount);
//    }
}
