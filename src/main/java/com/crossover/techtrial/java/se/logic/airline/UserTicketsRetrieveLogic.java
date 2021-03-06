package com.crossover.techtrial.java.se.logic.airline;

import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.dao.airline.AirlineDao;
import com.crossover.techtrial.java.se.model.user.UserTicket;
import com.crossover.techtrial.java.se.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class UserTicketsRetrieveLogic extends StatelessServiceLogic<List<UserTicket>, String> {

    @Autowired
    private AirlineDao airlineDao;

    @Transactional
    @Override
    public List<UserTicket> invoke(String userId) {

        ValidationUtil.validate(userId, "User id is null");
        List<UserTicket> userTickets = airlineDao.loadUserTicketsByUserId(Long.parseLong(userId));
        return userTickets;
    }
}
