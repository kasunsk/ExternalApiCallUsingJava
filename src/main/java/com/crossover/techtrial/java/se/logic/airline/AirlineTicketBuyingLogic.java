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
public class AirlineTicketBuyingLogic extends StatelessServiceLogic<AirlineTicket, TicketBuy> {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AirlineOfferLogicHelper helper;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private AirlineDao airlineDao;

    @Transactional
    @Override
    public AirlineTicket invoke(TicketBuy request) {

        validateTicketBuyingRequest(request);
        RestTemplate restTemplate = new RestTemplate();
        String accountCreateUrl
                = applicationProperties.getBaseAPIUrl() + applicationProperties.getApplicantId() + "/gammaairlines/tickets/buy";
        ResponseEntity<AirlineTicket> response = restTemplate.postForEntity(accountCreateUrl, request.getTicketBuyingRequest(), AirlineTicket.class);
        return response.getBody();
    }

    private void validateTicketBuyingRequest(TicketBuy request) {

        ValidationUtil.validate(request, "Request is null");
        ValidationUtil.validate(request.getApplicantId(), "Applicant Id is null");
        ValidationUtil.validate(request.getTicketBuyingRequest(), "Buying request is null");
        ValidationUtil.validate(request.getTicketBuyingRequest().getAccountId(), "Account id is null");
    }

    private void validateAirlineOfferInventoryAvailability(AirlineOfferModel airlineOffer, TicketBuyingRequest request) {

        Integer availableInventory = airlineOffer.getAvailbaleInventory();

//        if (availableInventory < request.getTicketAmount()) {
//            throw new ServiceRuntimeException(ErrorCode.NO_ENOUGH_INV, "No enough inventory for Airline Offer ");
//        }
    }

    private Price getConvertedOfferPrice(AirlineOfferModel airlineOffer, Currency accountCurrency) {

        Price price = new Price();
        price.setPrice(airlineOffer.getPrice());
        price.setCurrency(airlineOffer.getCurrency());

        if (!airlineOffer.getCurrency().equals(accountCurrency)) {
            CurrencyExchangeRequest exchangeRequest = new CurrencyExchangeRequest();
            exchangeRequest.setMonetaryAmount(price);
            exchangeRequest.setTargetCurrency(accountCurrency);
            price = accountService.exchangeCurrency(new ServiceRequest<>(exchangeRequest)).getPayload();
        }
        return price;
    }

    private Price calculatePaymentAmount(Double availableAmount, Price price, Integer ticketAmount) {

        BigDecimal payableAmount = BigDecimal.valueOf(price.getPrice()).multiply(new BigDecimal(ticketAmount));

        if (BigDecimal.valueOf(availableAmount).compareTo(payableAmount) == -1) {
            throw new ServiceRuntimeException(ErrorCode.NOT_ENOUGH_CREDIT, "Account Credits not enough to purchase");
        }

        Price amount = new Price();
        amount.setCurrency(price.getCurrency());
        amount.setPrice(payableAmount.doubleValue());
        return amount;
    }

    private void processPayment(Price price, BankAccount applicantBankAccount) {

//        Double newAccountBalance = applicantBankAccount.getAvailableAmount() - price.getPrice();
//        applicantBankAccount.setAvailableAmount(newAccountBalance);
//        accountDao.updateAccount(applicantBankAccount);
    }

    private UserTicket getUserTicket(String applicantId, AirlineOfferModel airlineOffer, TicketBuyingRequest request) {
        UserTicket userTicket = new UserTicket();
        userTicket.setUserId(Long.parseLong(applicantId));
        userTicket.setOfferId(airlineOffer.getOfferId());
        userTicket.setStatus(UserTicketStatus.BOUGHT);
        userTicket.setDestination(airlineOffer.getDestination());
        userTicket.setOrigin(airlineOffer.getOrigin());
        userTicket.setPrice(airlineOffer.getPrice());
        userTicket.setCurrency(airlineOffer.getCurrency());
//        userTicket.setTicketsAmount(request.getTicketAmount());
        return userTicket;
    }

    private void updateInventory(AirlineOfferModel airlineOffer, TicketBuyingRequest request) {

//        Integer newAvailableInventory = airlineOffer.getAvailbaleInventory() - request.getTicketAmount();
//        airlineOffer.setAvailbaleInventory(newAvailableInventory);
//        airlineDao.updateAirlineOffer(airlineOffer);

    }
}
