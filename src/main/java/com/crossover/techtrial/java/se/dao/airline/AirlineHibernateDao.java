package com.crossover.techtrial.java.se.dao.airline;

import com.crossover.techtrial.java.se.dao.AbstractDao;
import com.crossover.techtrial.java.se.dto.airline.AirlineOffer;
import com.crossover.techtrial.java.se.model.airline.AirlineOfferModel;
import com.crossover.techtrial.java.se.model.airline.Airport;
import com.crossover.techtrial.java.se.model.user.UserTicket;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository("airlineHibernateDao")
public class AirlineHibernateDao extends AbstractDao<Long, AirlineOfferModel> implements AirlineDao {

    public void saveAirlineOffer(AirlineOfferModel airlineOffer) {
        getSession().save(airlineOffer);
    }

    @SuppressWarnings("unchecked")
    public List<AirlineOfferModel> loadAirlineOffers(AirlineOffer.AirlineOfferStatus airlineOfferStatus) {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("from AirlineOfferModel AM");

        if (airlineOfferStatus != null) {
            queryBuilder.append(" WHERE ");
            queryBuilder.append("airlineOfferStatus=:airlineOfferStatus");
        }
        Query query = getSession().createQuery(queryBuilder.toString());
        return query.list();
    }

    public void updateAirlineOffer(AirlineOfferModel airlineOffer) {
        getSession().update(airlineOffer);
    }

    @Override
    public AirlineOfferModel loadOfferByRoute(String origin, String destination) {

        Query query = getSession().createQuery("from AirlineOfferModel AM where AM.origin=:origin and AM.destination=:destination");
        query.setParameter("origin", origin);
        query.setParameter("destination", destination);

        return (AirlineOfferModel) query.uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserTicket> loadApplicantAirlineOffers(Long applicantId) {

        Query query = getSession().createQuery("from UserTicket UT where UT.userId=:userId");
        query.setLong("userId", applicantId);
        return query.list();
    }

    @Override
    public UserTicket loadUserTicketById(Long userTicketId) {

        Query query = getSession().createQuery("from UserTicket UT where UT.id=:userTicketId");
        query.setParameter("userTicketId", userTicketId);
        return (UserTicket) query.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Airport> loadAllAirports() {

        Query sqlQuery = getSession().createQuery("from Airport");
        return sqlQuery.list();
    }

    @Override
    public void remove(String airlineOfferId) {
        AirlineOfferModel airlineOffer = loadAirlineOfferById(airlineOfferId);
        getSession().delete(airlineOffer);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserTicket> searchUserTicket(Long userId, String origin, String destination) {

        Query query;

        if (userId == null && origin == null && destination == null){
            query = getSession().createQuery("from UserTicket");
            return query.list();
        }
        return new ArrayList<>();
    }

    public AirlineOfferModel loadAirlineOfferById(String airlineOfferId) {
        Query query = getSession().createQuery("from AirlineOfferModel where offerId=:offerId");
        query.setParameter("offerId", Long.parseLong(airlineOfferId));
        return (AirlineOfferModel) query.uniqueResult();
    }
}
