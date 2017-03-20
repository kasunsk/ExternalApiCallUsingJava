package com.crossover.techtrial.java.se.dao.account;


import com.crossover.techtrial.java.se.model.account.BankAccount;
import com.crossover.techtrial.java.se.model.user.UserTicket;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

public class AccountHibernateDaoUnitTest {

    @InjectMocks
    AccountHibernateDao accountDao = new AccountHibernateDao();

    @Mock
    SessionFactory sessionFactory;

    @Mock
    Session session;

    @Mock
    Query query;

    @Mock
    SQLQuery sqlQuery;

    @BeforeMethod(alwaysRun = true)
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loadAccountByApplicantIdTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from BankAccount where USER_ID=:applicantId")).thenReturn(query);
        List<BankAccount> accounts = new ArrayList<>();
        when(query.list()).thenReturn(accounts);
        assertEquals(accountDao.loadAccountByApplicantId("23"), accounts);
    }

    @Test
    public void loadAccountByIdTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from BankAccount where accountId=:accountId")).thenReturn(query);
        BankAccount account = new BankAccount();
        when(query.uniqueResult()).thenReturn(account);
        assertEquals(accountDao.loadAccountById(3L), account);
    }


    @Test
    public void createAccountTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        accountDao.createAccount(new BankAccount());
        verify(session, times(1)).save(any());
    }


    @Test
    public void saveUserTicketTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        accountDao.saveUserTicket(new UserTicket());
        verify(session, times(1)).save(any());
    }

}
