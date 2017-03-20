package com.crossover.techtrial.java.se.logic.airline;

import com.crossover.techtrial.java.se.common.execption.ErrorCode;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.configuration.ApplicationProperties;
import com.crossover.techtrial.java.se.dao.account.AccountDao;
import com.crossover.techtrial.java.se.dto.airline.AirlineTicket;
import com.crossover.techtrial.java.se.dto.airline.TicketBuy;
import com.crossover.techtrial.java.se.model.user.UserTicket;
import com.crossover.techtrial.java.se.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    RestTemplate restTemplate;

    @Transactional
    @Override
    public UserTicket invoke(TicketBuy request) {

        validateTicketBuyingRequest(request);
        String ticketBiyUrl
                = applicationProperties.getBaseAPIUrl() + applicationProperties.getApplicantId() + "/gammaairlines/tickets/buy";

        UserTicket userTicket;
        ResponseEntity<AirlineTicket> response = restTemplate.postForEntity(ticketBiyUrl, request.getTicketBuyingRequest(), AirlineTicket.class);

        if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            throw new ServiceRuntimeException(ErrorCode.ACCOUNT_OR_ROUT_NOT_FOUND, "Account or route not found");
        } else if (response.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
            throw new ServiceRuntimeException(ErrorCode.INVALID_ACCOUNT, "Invalid account / Money not enough");
        }

        AirlineTicket airlineTicket = response.getBody();
        userTicket = getUserTicket(request, airlineTicket);
        accountDao.saveUserTicket(userTicket);
        return userTicket;
    }

    private UserTicket getUserTicket(TicketBuy request, AirlineTicket airlineTicket) {

        UserTicket userTicket = new UserTicket();
        userTicket.setUserId(Long.parseLong(request.getUserId()));
        userTicket.setCurrency(airlineTicket.getDetails().getPrice().getCurrency());

        BigDecimal ticketPrice = getTicketPrice(request, airlineTicket);
        userTicket.setPrice(ticketPrice.doubleValue());
        userTicket.setOrigin(airlineTicket.getDetails().getRoute().getFrom());
        userTicket.setDestination(airlineTicket.getDetails().getRoute().getTo());
        userTicket.setTicketsAmount(request.getTicketBuyingRequest().getAmount());
        return userTicket;
    }

    private BigDecimal getTicketPrice(TicketBuy request, AirlineTicket airlineTicket) {

        return BigDecimal.valueOf(airlineTicket.getDetails().getPrice().getAmount()).multiply(BigDecimal
                .valueOf(request.getTicketBuyingRequest().getAmount()));
    }

    private void validateTicketBuyingRequest(TicketBuy request) {

        ValidationUtil.validate(request, "Request is null");
        ValidationUtil.validate(request.getUserId(), "Applicant Id is null");
        ValidationUtil.validate(request.getTicketBuyingRequest(), "Buying request is null");
        ValidationUtil.validate(request.getTicketBuyingRequest().getAccountId(), "Account id is null");
        ValidationUtil.validate(request.getTicketBuyingRequest().getRoute(), "Route is empty");
        ValidationUtil.validate(request.getTicketBuyingRequest().getAmount(), "Ticket amount is null");
    }
}
