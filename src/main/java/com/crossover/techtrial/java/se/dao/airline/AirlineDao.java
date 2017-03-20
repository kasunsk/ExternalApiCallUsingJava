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

    void saveAirlineOffer(AirlineOfferModel airlineOffer);

    List<AirlineOfferModel> loadAirlineOffers(AirlineOffer.AirlineOfferStatus airlineOfferStatus);

    void updateAirlineOffer(AirlineOfferModel airlineOffer);

    AirlineOfferModel loadOfferByRoute(String origin, String destination);

    List<UserTicket> loadApplicantAirlineOffers(Long applicantId);

    UserTicket loadUserTicketById(Long userTicketId);

    List<Airport> loadAllAirports();

    void remove(String airlineOfferId);

    List<UserTicket> searchUserTicket(Long userId, String origin, String destination);
}
