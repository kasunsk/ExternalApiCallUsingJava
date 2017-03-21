package com.crossover.techtrial.java.se.dao.airline;


import com.crossover.techtrial.java.se.model.user.UserTicket;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

public class AirlineHibernateDaoUnitTest {

    @InjectMocks
    AirlineHibernateDao airlineDao = new AirlineHibernateDao();

    @Mock
    SessionFactory sessionFactory;

    @Mock
    Session session;

    @Mock
    Query query;

    @BeforeMethod(alwaysRun = true)
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void searchUserTicketAllInputNullTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from UserTicket")).thenReturn(query);
        List<UserTicket> userTickets = new ArrayList<>();
        when(query.uniqueResult()).thenReturn(userTickets);
        List<UserTicket> result = airlineDao.searchUserTicket(null, null, null);
        assertEquals(result, userTickets);
    }

    @Test
    public void searchUserTicketUserIdTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from UserTicket where userId=:userId")).thenReturn(query);
        List<UserTicket> userTickets = new ArrayList<>();
        when(query.uniqueResult()).thenReturn(userTickets);
        List<UserTicket> result = airlineDao.searchUserTicket(2L, null, null);
        assertEquals(result, userTickets);
    }

    @Test
    public void searchUserTicketUserIdOriginTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from UserTicket where userId=:userId and origin=:origin")).thenReturn(query);
        List<UserTicket> userTickets = new ArrayList<>();
        when(query.uniqueResult()).thenReturn(userTickets);
        List<UserTicket> result = airlineDao.searchUserTicket(2L, "Landon", null);
        assertEquals(result, userTickets);
    }

    @Test
    public void searchUserTicketUserIdOriginDestinationTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from UserTicket where userId=:userId and origin=:origin and destination=:destination")).thenReturn(query);
        List<UserTicket> userTickets = new ArrayList<>();
        when(query.uniqueResult()).thenReturn(userTickets);
        List<UserTicket> result = airlineDao.searchUserTicket(2L, "Landon", "New York");
        assertEquals(result, userTickets);
    }


    @Test
    public void searchUserTicketOriginDestinationTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from UserTicket where origin=:origin and destination=:destination")).thenReturn(query);
        List<UserTicket> userTickets = new ArrayList<>();
        when(query.uniqueResult()).thenReturn(userTickets);
        List<UserTicket> result = airlineDao.searchUserTicket(null, "Landon", "New York");
        assertEquals(result, userTickets);
    }

    @Test
    public void searchUserTicketDestinationTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from UserTicket where destination=:destination")).thenReturn(query);
        List<UserTicket> userTickets = new ArrayList<>();
        when(query.uniqueResult()).thenReturn(userTickets);
        List<UserTicket> result = airlineDao.searchUserTicket(null, null, "New York");
        assertEquals(result, userTickets);
    }

    @Test
    public void searchUserTicketUserIdDestinationTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from UserTicket where userId=:userId and destination=:destination")).thenReturn(query);
        List<UserTicket> userTickets = new ArrayList<>();
        when(query.uniqueResult()).thenReturn(userTickets);
        List<UserTicket> result = airlineDao.searchUserTicket(2L, null, "New York");
        assertEquals(result, userTickets);
    }

    @Test
    public void searchUserTicketOriginTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from UserTicket where origin=:origin")).thenReturn(query);
        List<UserTicket> userTickets = new ArrayList<>();
        when(query.uniqueResult()).thenReturn(userTickets);
        List<UserTicket> result = airlineDao.searchUserTicket(null, "London", null);
        assertEquals(result, userTickets);
    }


    @Test
    public void loadApplicantAirlineOffersTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from UserTicket UT where UT.userId=:userId")).thenReturn(query);
        List<UserTicket> userTickets = new ArrayList<>();
        when(query.uniqueResult()).thenReturn(userTickets);
        assertEquals(userTickets, airlineDao.loadUserTicketsByUserId(25L));
    }

    @Test
    public void loadUserTicketByIdTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from UserTicket UT where UT.id=:userTicketId")).thenReturn(query);
        UserTicket userTicket = new UserTicket();
        when(query.uniqueResult()).thenReturn(userTicket);
        assertEquals(userTicket, airlineDao.loadUserTicketById(25L));
    }

}
