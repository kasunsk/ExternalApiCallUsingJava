package com.crossover.techtrial.java.se.dao.airline;

import com.crossover.techtrial.java.se.dto.airline.AirlineOffer;
import com.crossover.techtrial.java.se.model.airline.AirlineOfferModel;
import com.crossover.techtrial.java.se.model.airline.Airport;
import com.crossover.techtrial.java.se.model.user.UserTicket;

import java.util.List;

/**
 * This interface provide the database access methods
 */
public interface AirlineDao {


    List<UserTicket> loadApplicantAirlineOffers(Long applicantId);

    UserTicket loadUserTicketById(Long userTicketId);

    List<UserTicket> searchUserTicket(Long userId, String origin, String destination);
}
