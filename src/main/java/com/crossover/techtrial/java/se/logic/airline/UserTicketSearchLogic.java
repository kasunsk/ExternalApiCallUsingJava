package com.crossover.techtrial.java.se.logic.airline;

import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.dao.airline.AirlineDao;
import com.crossover.techtrial.java.se.dao.user.UserDao;
import com.crossover.techtrial.java.se.dto.user.UserTicketSearchCriteria;
import com.crossover.techtrial.java.se.logic.user.UserLogicHelper;
import com.crossover.techtrial.java.se.model.user.User;
import com.crossover.techtrial.java.se.model.user.UserTicket;
import com.crossover.techtrial.java.se.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

import static com.crossover.techtrial.java.se.util.ValidationUtil.validate;

@Component("userTicketSearchLogic")
public class UserTicketSearchLogic extends StatelessServiceLogic<List<UserTicket>, UserTicketSearchCriteria> {

    @Autowired
    private AirlineDao airlineDao;

    @Autowired
    private UserLogicHelper userLogicHelper;

    @Transactional
    @Override
    public List<UserTicket> invoke(UserTicketSearchCriteria criteria) {

        validate(criteria, "Search Criteria is null");
        Long userId = null;

        if (criteria.getEmail() != null) {
            User user = userLogicHelper.loadUserByEmail(criteria.getEmail());
            userId = user.getUserId();
        }
        return airlineDao.searchUserTicket(userId, criteria.getOrigin(), criteria.getDestination());
    }
}
