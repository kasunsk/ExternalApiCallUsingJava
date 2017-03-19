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
    public void saveAirlineOfferTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        airlineDao.saveAirlineOffer(new AirlineOfferModel());
        verify(session, times(1)).save(any());
    }

    @Test
    public void loadAirlineOffersWithoutStatusTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from AirlineOfferModel AM")).thenReturn(query);
        List<AirlineOfferModel> airlineOfferModels = new ArrayList<>();
        when(query.list()).thenReturn(airlineOfferModels);
        assertEquals(airlineOfferModels, airlineDao.loadAirlineOffers(null));
    }

    @Test
    public void loadAirlineOffersWithStatusTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from AirlineOfferModel AM WHERE airlineOfferStatus=:airlineOfferStatus")).thenReturn(query);
        List<AirlineOfferModel> airlineOfferModels = new ArrayList<>();
        when(query.list()).thenReturn(airlineOfferModels);
        assertEquals(airlineOfferModels, airlineDao.loadAirlineOffers(AirlineOffer.AirlineOfferStatus.AVAILABLE));
    }

    @Test
    public void updateAirlineOfferTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        airlineDao.updateAirlineOffer(new AirlineOfferModel());
        verify(session, times(1)).update(any());
    }

    @Test
    public void loadOfferByRouteTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from AirlineOfferModel AM where AM.origin=:origin and AM.destination=:destination")).thenReturn(query);
        AirlineOfferModel airlineOfferModel = new AirlineOfferModel();
        when(query.uniqueResult()).thenReturn(airlineOfferModel);
        assertEquals(airlineOfferModel, airlineDao.loadOfferByRoute("origin", "destination"));
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

    @Test
    public void loadAllAirportsTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from Airport")).thenReturn(query);
        List<Airport> airports = new ArrayList<>();
        when(query.uniqueResult()).thenReturn(airports);
        assertEquals(airports, airlineDao.loadAllAirports());
    }

    @Test
    public void removeTest() {
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from AirlineOfferModel where offerId=:offerId")).thenReturn(query);
        when(query.uniqueResult()).thenReturn(new AirlineOfferModel());
        airlineDao.remove("5");
        verify(session, times(1)).delete(any());
    }
}
