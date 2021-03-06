package com.crossover.techtrial.java.se.controller;

import com.crossover.techtrial.java.se.common.dto.*;
import com.crossover.techtrial.java.se.common.dto.Void;
import com.crossover.techtrial.java.se.dto.airline.*;
import com.crossover.techtrial.java.se.dto.email.EmailParam;
import com.crossover.techtrial.java.se.dto.user.UserTicketSearchCriteria;
import com.crossover.techtrial.java.se.model.airline.Airport;
import com.crossover.techtrial.java.se.model.user.UserTicket;
import com.crossover.techtrial.java.se.service.airline.AirlineService;
import com.crossover.techtrial.java.se.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class provide http rest interface for Airline Services
 */
@Controller
public class AirlineController {

    @Autowired
    private AirlineService airlineService;

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/{userId}/gammaairlines/offers", method = RequestMethod.GET)
    @ResponseBody
    public List<GammaAirlineOffer> retrieveAvailableOffers(@PathVariable("userId") String userId) {
        OfferRequest offerRequest = new OfferRequest();
        offerRequest.setUserId(userId);
        ServiceResponse<List<GammaAirlineOffer>> response = airlineService.retrieveAvailableAirlineOffers(new ServiceRequest<>(offerRequest));
        return response.getPayload();
    }

    @RequestMapping(value = "/{userId}/gammaairlines/tickets", method = RequestMethod.GET)
    @ResponseBody
    public List<UserTicket> retrieveApplicantTickets(@PathVariable("userId") String userId) {

        return airlineService.retrieveUserTickets(new ServiceRequest<>(userId)).getPayload();
    }


    @RequestMapping(value = "/{userId}/gammaairlines/offers/buy", method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public UserTicket buyOffer(@PathVariable("userId") String userId, @RequestBody TicketBuyingRequest buyingRequest) {

        TicketBuy ticketBuy = new TicketBuy();
        ticketBuy.setTicketBuyingRequest(buyingRequest);
        ticketBuy.setUserId(userId);
        ServiceRequest<TicketBuy> serviceRequest = new ServiceRequest<>(ticketBuy);
        return airlineService.buyAirlineTicket(serviceRequest).getPayload();
    }

    @RequestMapping(value = "/{userId}/gammaairlines/userTicket/search/", method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<UserTicket> search(@PathVariable("userId") String userId, @RequestBody UserTicketSearchCriteria userTicketSearchCriteria) {

        ServiceRequest<UserTicketSearchCriteria> serviceRequest = new ServiceRequest<>(userTicketSearchCriteria);
        return airlineService.searchUserTicket(serviceRequest).getPayload();
    }


    @RequestMapping(value = "/gammaairlines/userTicket/email/send/{userTicketId}", method = RequestMethod.GET)
    @ResponseBody
    public Boolean sendUserTicketEmail(@PathVariable String userTicketId) {

        return airlineService.sendUserTicketEmail(new ServiceRequest<>(userTicketId)).getPayload();
    }

    @RequestMapping(value = "/gammaairlines/email/send", method = RequestMethod.POST)
    @ResponseBody
    public Boolean sendEmail(@RequestBody EmailParam emailParam) {

        emailService.sendEmail(new ServiceRequest<>(emailParam));
        return Boolean.TRUE;
    }

    @RequestMapping(value = "/{applicantId}/gammaairlines/tickets/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public List<UserTicket> loadUsersTickers(@PathVariable("applicantId") String userTicket, @PathVariable("userId") String userId) {

        return airlineService.retrieveUserTickets(new ServiceRequest<>(userId)).getPayload();
    }

}
