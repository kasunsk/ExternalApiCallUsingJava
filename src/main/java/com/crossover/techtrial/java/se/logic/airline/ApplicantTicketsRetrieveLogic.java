package com.crossover.techtrial.java.se.logic.airline;

import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.configuration.ApplicationProperties;
import com.crossover.techtrial.java.se.dao.account.AccountDao;
import com.crossover.techtrial.java.se.dao.airline.AirlineDao;
import com.crossover.techtrial.java.se.dto.airline.AirlineTicket;
import com.crossover.techtrial.java.se.dto.airline.GammaAirlineOffer;
import com.crossover.techtrial.java.se.model.user.UserTicket;
import com.crossover.techtrial.java.se.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Component
public class ApplicantTicketsRetrieveLogic extends StatelessServiceLogic<List<UserTicket>, String> {

    @Autowired
    private AirlineDao airlineDao;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Transactional
    @Override
    public List<UserTicket> invoke(String userId) {

        ValidationUtil.validate(userId, "Applicant id is null");
        RestTemplate restTemplate = new RestTemplate();

        String availableOfferUrl
                = applicationProperties.getBaseAPIUrl() + applicationProperties.getApplicantId() + "/gammaairlines/tickets";

        ResponseEntity<AirlineTicket[]> responseEntity = restTemplate.getForEntity(availableOfferUrl, AirlineTicket[].class);
        AirlineTicket[] airlineTickets = responseEntity.getBody();

        List<UserTicket> userTickets = airlineDao.loadApplicantAirlineOffers(Long.parseLong(userId));

        return userTickets;
    }
}
