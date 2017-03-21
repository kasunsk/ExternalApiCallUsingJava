package com.crossover.techtrial.java.se.dao.airline;

import com.crossover.techtrial.java.se.dao.AbstractDao;
import com.crossover.techtrial.java.se.dto.airline.GammaAirlineOffer;
import com.crossover.techtrial.java.se.model.user.UserTicket;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("airlineHibernateDao")
public class AirlineHibernateDao extends AbstractDao<Long, GammaAirlineOffer> implements AirlineDao {


    @Override
    @SuppressWarnings("unchecked")
    public List<UserTicket> loadUserTicketsByUserId(Long userId) {

        Query query = getSession().createQuery("from UserTicket UT where UT.userId=:userId");
        query.setLong("userId", userId);
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
        } else if (userId == null && origin != null && destination == null) {
            query = getSession().createQuery("from UserTicket where origin=:origin");
            query.setParameter("origin", origin);
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
