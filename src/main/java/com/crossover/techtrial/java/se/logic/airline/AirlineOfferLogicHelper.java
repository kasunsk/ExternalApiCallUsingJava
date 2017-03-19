package com.crossover.techtrial.java.se.logic.airline;

import com.crossover.techtrial.java.se.common.dto.ServiceRequest;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.dao.airline.AirlineDao;
import com.crossover.techtrial.java.se.model.airline.AirlineOfferModel;
import com.crossover.techtrial.java.se.model.airline.Route;
import com.crossover.techtrial.java.se.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This helper class will provide common helper methods for Airline Logic classes.
 */
@Component
public class AirlineOfferLogicHelper {

    @Autowired
    private AirlineDao airlineDao;

    @Autowired
    private UserService userService;

    protected AirlineOfferModel loadOfferByRout(Route airlineRout) {

        if (airlineRout == null || airlineRout.getFrom() == null || airlineRout.getTo() == null) {
            throw new ServiceRuntimeException("Invalid airline route");
        }
        return airlineDao.loadOfferByRoute(airlineRout.getFrom(), airlineRout.getTo());
    }

    protected void authenticateApplicant(String applicantId) {

        if (applicantId == null || applicantId.isEmpty()) {
            throw new ServiceRuntimeException("Invalid applicant id");
        }
        userService.authenticateUser(new ServiceRequest<>(applicantId));
    }
}
