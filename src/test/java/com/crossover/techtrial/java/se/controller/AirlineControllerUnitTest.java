package com.crossover.techtrial.java.se.controller;


import com.crossover.techtrial.java.se.common.dto.ServiceRequest;
import com.crossover.techtrial.java.se.common.dto.ServiceResponse;
import com.crossover.techtrial.java.se.dto.airline.GammaAirlineOffer;
import com.crossover.techtrial.java.se.dto.airline.TicketBuyingRequest;
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

    @SuppressWarnings("unchecked")
    @Test
    public void buyOfferTest() {

        ServiceResponse<UserTicket> response = new ServiceResponse<>();
        UserTicket userTicket = new UserTicket();
        response.setPayload(userTicket);
        when(airlineService.buyAirlineTicket(Matchers.<ServiceRequest>any())).thenReturn(response);
        TicketBuyingRequest ticketBuyingRequest = new TicketBuyingRequest();
        assertEquals(userTicket, controller.buyOffer("12", ticketBuyingRequest));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void retrieveApplicantTicketsTest() {

        ServiceResponse<List<UserTicket>> response = new ServiceResponse<>();
        List<UserTicket> userTickets = new ArrayList<>();
        response.setPayload(userTickets);
        when(airlineService.retrieveUserTickets(Matchers.<ServiceRequest>any())).thenReturn(response);
        assertEquals(userTickets, controller.retrieveApplicantTickets("12"));
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

        ServiceResponse<List<UserTicket>> response = new ServiceResponse<>();
        List<UserTicket> userTickets = new ArrayList<>();
        response.setPayload(userTickets);
        when(airlineService.retrieveUserTickets(Matchers.<ServiceRequest>any())).thenReturn(response);
        assertEquals(userTickets, controller.loadUsersTickers("12", "23"));
    }

}
