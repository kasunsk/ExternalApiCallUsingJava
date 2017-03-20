package com.crossover.techtrial.java.se.dao.airline;


import com.crossover.techtrial.java.se.dto.airline.AirlineOffer;
import com.crossover.techtrial.java.se.model.airline.AirlineOfferModel;
import com.crossover.techtrial.java.se.model.airline.Airport;
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

import static org.mockito.Matchers.any;
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
    public void loadApplicantAirlineOffersTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from UserTicket UT where UT.userId=:userId")).thenReturn(query);
        List<UserTicket> userTickets = new ArrayList<>();
        when(query.uniqueResult()).thenReturn(userTickets);
        assertEquals(userTickets, airlineDao.loadApplicantAirlineOffers(25L));
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
