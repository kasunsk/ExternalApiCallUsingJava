package com.crossover.techtrial.java.se.controller;

import com.crossover.techtrial.java.se.common.dto.*;
import com.crossover.techtrial.java.se.common.dto.Void;
import com.crossover.techtrial.java.se.dto.airline.*;
import com.crossover.techtrial.java.se.dto.email.EmailParam;
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

    @RequestMapping(value = "/gammaairlines/offers/save", method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Boolean createOffers(@RequestBody AirlineOffer airlineOffer) {

        airlineService.createAirlineOffer(new ServiceRequest<>(airlineOffer));
        return Boolean.TRUE;
    }

    @RequestMapping(value = "/{userId}/gammaairlines/tickets", method = RequestMethod.GET)
    @ResponseBody
    public List<AirlineTicket> retrieveApplicantTickets(@PathVariable("userId") String userId) {

        return airlineService.retrieveApplicantTickets(new ServiceRequest<>(userId)).getPayload();
    }


    @RequestMapping(value = "/{userId}/gammaairlines/offers/buy", method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public AirlineTicket buyOffer(@PathVariable("userId") String userId, @RequestBody TicketBuyingRequest buyingRequest) {

        TicketBuy ticketBuy = new TicketBuy();
        ticketBuy.setTicketBuyingRequest(buyingRequest);
        ticketBuy.setApplicantId(userId);
        ServiceRequest<TicketBuy> serviceRequest = new ServiceRequest<>(ticketBuy);
        return airlineService.buyAirlineTicket(serviceRequest).getPayload();
    }

    @RequestMapping(value = "/gammaairlines/country/all", method = RequestMethod.GET)
    @ResponseBody
    public List<Airport> loadAllAirports() {

        return airlineService.loadAllAirports(new ServiceRequest<>(new Void())).getPayload();
    }

    @RequestMapping(value = "/gammaairlines/offer/remove/{offerId}", method = RequestMethod.GET)
    @ResponseBody
    public Boolean removeOffer(@PathVariable("offerId") String offerId) {

        airlineService.removeAirlineOffer(new ServiceRequest<>(offerId));
        return Boolean.TRUE;
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
    public List<AirlineTicket> loadUsersTickers(@PathVariable("applicantId") String applicantId, @PathVariable("userId") String userId) {

        return airlineService.retrieveApplicantTickets(new ServiceRequest<>(userId)).getPayload();
    }


}
