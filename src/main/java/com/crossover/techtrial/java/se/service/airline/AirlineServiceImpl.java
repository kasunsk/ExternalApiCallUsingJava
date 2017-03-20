package com.crossover.techtrial.java.se.service.airline;

import com.crossover.techtrial.java.se.common.dto.*;
import com.crossover.techtrial.java.se.common.dto.Void;
import com.crossover.techtrial.java.se.common.service.RequestAssembler;
import com.crossover.techtrial.java.se.dto.airline.*;
import com.crossover.techtrial.java.se.dto.user.UserTicketSearchCriteria;
import com.crossover.techtrial.java.se.logic.airline.*;
import com.crossover.techtrial.java.se.model.airline.Airport;
import com.crossover.techtrial.java.se.model.user.UserTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("airlineService")
public class AirlineServiceImpl implements AirlineService {

    @Autowired
    private AvailableAirlineOfferRetrieveLogic availableAirlineOfferRetrieveLogic;

    @Autowired
    private ApplicantTicketsRetrieveLogic applicantTicketsRetrieveLogic;

    @Autowired
    private AirlineTicketBuyingLogic airlineTicketBuyingLogic;

    @Autowired
    private UserTicketEmailSendingLogic userTicketEmailSendingLogic;

    @Autowired
    private UserTicketSearchLogic userTicketSearchLogic;

    @Override
    public ServiceResponse<List<GammaAirlineOffer>> retrieveAvailableAirlineOffers(ServiceRequest<OfferRequest> offerRequest) {

        return RequestAssembler.assemble(availableAirlineOfferRetrieveLogic, offerRequest);
    }

    @Override
    public ServiceResponse<List<UserTicket>> retrieveUserTickets(ServiceRequest<String> userId) {

        return RequestAssembler.assemble(applicantTicketsRetrieveLogic, userId);
    }


    @Override
    public ServiceResponse<UserTicket> buyAirlineTicket(ServiceRequest<TicketBuy> request) {

        return RequestAssembler.assemble(airlineTicketBuyingLogic, request);
    }

    @Override
    public ServiceResponse<Boolean> sendUserTicketEmail(ServiceRequest<String> userTicketId) {

        return RequestAssembler.assemble(userTicketEmailSendingLogic, userTicketId);
    }

    @Override
    public ServiceResponse<List<UserTicket>> searchUserTicket(ServiceRequest<UserTicketSearchCriteria> criteria) {

        return RequestAssembler.assemble(userTicketSearchLogic, criteria);
    }

}
