package com.crossover.techtrial.java.se.controller;


import com.crossover.techtrial.java.se.common.dto.ServiceRequest;
import com.crossover.techtrial.java.se.common.dto.ServiceResponse;
import com.crossover.techtrial.java.se.dto.airline.AirlineOffer;
import com.crossover.techtrial.java.se.dto.airline.AirlineTicket;
import com.crossover.techtrial.java.se.dto.airline.GammaAirlineOffer;
import com.crossover.techtrial.java.se.dto.airline.TicketBuyingRequest;
import com.crossover.techtrial.java.se.model.airline.Airport;
import com.crossover.techtrial.java.se.model.user.UserTicket;
import com.crossover.techtrial.java.se.service.airline.AirlineService;
import com.crossover.techtrial.java.se.service.email.EmailService;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AirlineControllerUnitTest {

    @InjectMocks
    AirlineController controller = new AirlineController();

    @Mock
    private AirlineService airlineService;

    @Mock
    private EmailService emailService;

    @BeforeMethod(alwaysRun = true)
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void retrieveAvailableOffersTest() {

        List<GammaAirlineOffer> airlineOffers = new ArrayList<>();
        ServiceResponse<List<GammaAirlineOffer>> response = new ServiceResponse<>();
        response.setPayload(airlineOffers);
        when(airlineService.retrieveAvailableAirlineOffers(Matchers.<ServiceRequest>any())).thenReturn(response);
        assertEquals(airlineOffers, controller.retrieveAvailableOffers("23"));
    }

    @Test
    public void createOffersTest() {
        assertTrue(controller.createOffers(new AirlineOffer()));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void buyOfferTest() {

        ServiceResponse<AirlineTicket> response = new ServiceResponse<>();
        AirlineTicket userTicket = new AirlineTicket();
        response.setPayload(userTicket);
        when(airlineService.buyAirlineTicket(Matchers.<ServiceRequest>any())).thenReturn(response);
        TicketBuyingRequest ticketBuyingRequest = new TicketBuyingRequest();
        assertEquals(userTicket, controller.buyOffer("12", ticketBuyingRequest));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void retrieveApplicantTicketsTest() {

        ServiceResponse<List<AirlineTicket>> response = new ServiceResponse<>();
        List<AirlineTicket> userTickets = new ArrayList<>();
        response.setPayload(userTickets);
        when(airlineService.retrieveApplicantTickets(Matchers.<ServiceRequest>any())).thenReturn(response);
        assertEquals(userTickets, controller.retrieveApplicantTickets("12"));
    }

    @Test
    public void removeOfferTest() {

        assertTrue(controller.removeOffer("12"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void loadAllAirportsTest() {

        List<Airport> airports = new ArrayList<>();
        ServiceResponse<List<Airport>> response = new ServiceResponse<>();
        response.setPayload(airports);
        when(airlineService.loadAllAirports(Matchers.<ServiceRequest>any())).thenReturn(response);
        controller.loadAllAirports();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void sendUserTicketEmailTest() {

        ServiceResponse<Boolean> response = new ServiceResponse<>();
        response.setPayload(Boolean.TRUE);
        when(airlineService.sendUserTicketEmail(Matchers.<ServiceRequest>any())).thenReturn(response);
        assertTrue(controller.sendUserTicketEmail("2"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void loadUsersTickersTest() {

        ServiceResponse<List<AirlineTicket>> response = new ServiceResponse<>();
        List<AirlineTicket> userTickets = new ArrayList<>();
        response.setPayload(userTickets);
        when(airlineService.retrieveApplicantTickets(Matchers.<ServiceRequest>any())).thenReturn(response);
        assertEquals(userTickets, controller.loadUsersTickers("12", "23"));
    }

}
