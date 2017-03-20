package com.crossover.techtrial.java.se.service.airline;

import com.crossover.techtrial.java.se.common.dto.*;
import com.crossover.techtrial.java.se.common.dto.Void;
import com.crossover.techtrial.java.se.dto.airline.*;
import com.crossover.techtrial.java.se.model.airline.Airport;
import com.crossover.techtrial.java.se.model.user.UserTicket;

import java.util.List;

/**
 * This API provide all the service method for airline service
 */
public interface AirlineService {

    /**
     *
     * @param offerRequest
     * @return List of AirlineOffer
     */
    ServiceResponse<List<GammaAirlineOffer>> retrieveAvailableAirlineOffers(ServiceRequest<OfferRequest> offerRequest);

    /**
     *
     * @param applicantId
     * @return List of UserTicket
     */
    ServiceResponse<List<UserTicket>> retrieveUserTickets(ServiceRequest<String> applicantId);

    /**
     *
     * @param ticketBuyRequest
     * @return UserTicket
     */
    ServiceResponse<UserTicket> buyAirlineTicket(ServiceRequest<TicketBuy> ticketBuyRequest);

    /**
     *
     * @param userTicketId
     * @return
     */
    ServiceResponse<Boolean> sendUserTicketEmail(ServiceRequest<String> userTicketId);
}
