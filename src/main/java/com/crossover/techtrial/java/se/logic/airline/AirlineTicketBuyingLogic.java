package com.crossover.techtrial.java.se.logic.airline;

import com.crossover.techtrial.java.se.common.dto.*;
import com.crossover.techtrial.java.se.common.execption.ErrorCode;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.configuration.ApplicationProperties;
import com.crossover.techtrial.java.se.dao.account.AccountDao;
import com.crossover.techtrial.java.se.dao.airline.AirlineDao;
import com.crossover.techtrial.java.se.dto.airline.AirlineTicket;
import com.crossover.techtrial.java.se.dto.airline.TicketBuy;
import com.crossover.techtrial.java.se.dto.airline.TicketBuyingRequest;
import com.crossover.techtrial.java.se.logic.account.Account;
import com.crossover.techtrial.java.se.model.account.BankAccount;
import com.crossover.techtrial.java.se.model.airline.AirlineOfferModel;
import com.crossover.techtrial.java.se.model.user.UserTicket;
import com.crossover.techtrial.java.se.service.account.AccountService;
import com.crossover.techtrial.java.se.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.math.BigDecimal;

/**
 * This class provide logic of buying airline tickets
 */
@Component
public class AirlineTicketBuyingLogic extends StatelessServiceLogic<UserTicket, TicketBuy> {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Transactional
    @Override
    public UserTicket invoke(TicketBuy request) {

        validateTicketBuyingRequest(request);
        RestTemplate restTemplate = new RestTemplate();
        String accountCreateUrl
                = applicationProperties.getBaseAPIUrl() + applicationProperties.getApplicantId() + "/gammaairlines/tickets/buy";
        ResponseEntity<AirlineTicket> response = restTemplate.postForEntity(accountCreateUrl, request.getTicketBuyingRequest(), AirlineTicket.class);

        AirlineTicket airlineTicket = response.getBody();

        UserTicket userTicket = new UserTicket();
        userTicket.setUserId(Long.parseLong(request.getUserId()));
        userTicket.setCurrency(airlineTicket.getDetails().getPrice().getCurrency());
        userTicket.setPrice(airlineTicket.getDetails().getPrice().getAmount());
        userTicket.setOrigin(airlineTicket.getDetails().getRoute().getFrom());
        userTicket.setDestination(airlineTicket.getDetails().getRoute().getTo());
        userTicket.setTicketsAmount(request.getTicketBuyingRequest().getAmount());
        accountDao.saveUserTicket(userTicket);
        return userTicket;
    }

    private void validateTicketBuyingRequest(TicketBuy request) {

        ValidationUtil.validate(request, "Request is null");
        ValidationUtil.validate(request.getUserId(), "Applicant Id is null");
        ValidationUtil.validate(request.getTicketBuyingRequest(), "Buying request is null");
        ValidationUtil.validate(request.getTicketBuyingRequest().getAccountId(), "Account id is null");
    }
}
