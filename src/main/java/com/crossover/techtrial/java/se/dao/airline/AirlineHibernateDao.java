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
    public List<UserTicket> searchUserTicket(Long userId, String origin, String destination) {

        Query query;

        if (userId == null && origin == null && destination == null) {
            query = getSession().createQuery("from UserTicket");
            return query.list();
        } else if (userId != null && origin == null && destination == null) {
            query = getSession().createQuery("from UserTicket where userId=:userId");
            query.setParameter("userId", userId);
            return query.list();
        } else if (userId != null && origin != null && destination == null) {
            query = getSession().createQuery("from UserTicket where userId=:userId and origin=:origin");
            query.setParameter("userId", userId);
            query.setParameter("origin", origin);
            return query.list();
        } else if (userId == null && origin != null && destination != null) {
            query = getSession().createQuery("from UserTicket where origin=:origin and destination=:destination");
            query.setParameter("origin", origin);
            query.setParameter("destination", destination);
            return query.list();
        } else if (userId != null && origin != null) {
            query = getSession().createQuery("from UserTicket where userId=:userId and origin=:origin and destination=:destination");
            query.setParameter("userId", userId);
            query.setParameter("origin", origin);
            query.setParameter("destination", destination);
            return query.list();
        } else if (userId == null && origin == null) {
            query = getSession().createQuery("from UserTicket where destination=:destination");
            query.setParameter("destination", destination);
            return query.list();
        } else {
            query = getSession().createQuery("from UserTicket where userId=:userId and destination=:destination");
            query.setParameter("destination", destination);
            return query.list();
        }
    }

}
