package com.crossover.techtrial.java.se.logic.airline;


import com.crossover.techtrial.java.se.common.dto.Currency;
import com.crossover.techtrial.java.se.common.dto.Price;
import com.crossover.techtrial.java.se.common.dto.ServiceRequest;
import com.crossover.techtrial.java.se.common.dto.ServiceResponse;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.configuration.ApplicationProperties;
import com.crossover.techtrial.java.se.dao.account.AccountDao;
import com.crossover.techtrial.java.se.dao.airline.AirlineDao;
import com.crossover.techtrial.java.se.dto.airline.AirlineTicket;
import com.crossover.techtrial.java.se.dto.airline.GammaAirlineOffer;
import com.crossover.techtrial.java.se.dto.airline.TicketBuy;
import com.crossover.techtrial.java.se.dto.airline.TicketBuyingRequest;
import com.crossover.techtrial.java.se.model.account.BankAccount;
import com.crossover.techtrial.java.se.model.airline.AirlineOfferModel;
import com.crossover.techtrial.java.se.model.airline.Route;
import com.crossover.techtrial.java.se.model.user.UserTicket;
import com.crossover.techtrial.java.se.service.account.AccountService;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class AirlineTicketBuyingLogicUnitTest {

    @InjectMocks
    AirlineTicketBuyingLogic logic = new AirlineTicketBuyingLogic();

    @Mock
    private AccountDao accountDao;

    @Mock
    ApplicationProperties applicationProperties;

    @Mock
    RestTemplate restTemplate;


    @BeforeMethod(alwaysRun = true)
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void buyingRequestValidateNull() {
        logic.invoke(null);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void applicantIdNullTest() {
        logic.invoke(new TicketBuy());
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void buyingRequestNullTest() {
        TicketBuy ticketBuy = new TicketBuy();
        ticketBuy.setUserId("2");
        logic.invoke(ticketBuy);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void accountIdNullTest() {
        TicketBuy ticketBuy = new TicketBuy();
        ticketBuy.setUserId("2");
        ticketBuy.setTicketBuyingRequest(new TicketBuyingRequest());
        logic.invoke(ticketBuy);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void routNullTest() {
        TicketBuy ticketBuy = new TicketBuy();
        ticketBuy.setUserId("3");
        TicketBuyingRequest buyingRequest = new TicketBuyingRequest();
        buyingRequest.setAccountId("accountId");
        ticketBuy.setTicketBuyingRequest(buyingRequest);
        logic.invoke(ticketBuy);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void amountNullTest() {
        TicketBuy ticketBuy = new TicketBuy();
        ticketBuy.setUserId("2");
        TicketBuyingRequest buyingRequest = new TicketBuyingRequest();
        buyingRequest.setAccountId("accountId");
        buyingRequest.setRoute(new Route());
        ticketBuy.setTicketBuyingRequest(buyingRequest);
        logic.invoke(ticketBuy);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void invalidBankAccountTest() {
        TicketBuy ticketBuy = getTicketBuy();
        when(accountDao.loadAccountById(12L)).thenReturn(null);
        logic.invoke(ticketBuy);
    }


    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void invokeFailBadRequestTest() {

        when(applicationProperties.getBaseAPIUrl()).thenReturn("https://api-forest.crossover.com/");
        when(applicationProperties.getApplicantId()).thenReturn("zzzz");

        ResponseEntity<AirlineTicket> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        TicketBuy ticketBuy = getTicketBuy();
        when(restTemplate.postForEntity(anyString(), any(), eq(AirlineTicket.class))).thenReturn(response);
        logic.invoke(ticketBuy);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void invokeFailNotFoundTest() {

        when(applicationProperties.getBaseAPIUrl()).thenReturn("https://api-forest.crossover.com/");
        when(applicationProperties.getApplicantId()).thenReturn("zzzz");

        ResponseEntity<AirlineTicket> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        TicketBuy ticketBuy = getTicketBuy();
        when(restTemplate.postForEntity(anyString(), any(), eq(AirlineTicket.class))).thenReturn(response);
        logic.invoke(ticketBuy);
    }

    @Test
    public void invokeTest() {

        when(applicationProperties.getBaseAPIUrl()).thenReturn("https://api-forest.crossover.com/");
        when(applicationProperties.getApplicantId()).thenReturn("zzzz");

        AirlineTicket airlineTicket = getAirlineTicket();

        ResponseEntity<AirlineTicket> response = new ResponseEntity<>(airlineTicket, HttpStatus.OK);
        TicketBuy ticketBuy = getTicketBuy();
        when(restTemplate.postForEntity(anyString(), any(), eq(AirlineTicket.class))).thenReturn(response);

        assertNotNull(logic.invoke(ticketBuy));
    }

    private AirlineTicket getAirlineTicket() {
        AirlineTicket airlineTicket = new AirlineTicket();
        airlineTicket.setAmount(5);
        GammaAirlineOffer gammaAirlineOffer = new GammaAirlineOffer();
        com.crossover.techtrial.java.se.dto.airline.Price price = new com.crossover.techtrial.java.se.dto.airline.Price();
        price.setAmount(100D);
        price.setCurrency(Currency.AED);
        gammaAirlineOffer.setPrice(price);
        Route route = new Route();
        route.setFrom("LL");
        route.setTo("AA");
        gammaAirlineOffer.setRoute(route);
        airlineTicket.setDetails(gammaAirlineOffer);
        return airlineTicket;
    }

    private TicketBuy getTicketBuy() {
        TicketBuy ticketBuy = new TicketBuy();
        ticketBuy.setUserId("5");
        TicketBuyingRequest buyingRequest = new TicketBuyingRequest();
        buyingRequest.setAccountId("12");
        buyingRequest.setRoute(new Route());
        buyingRequest.setAmount(3);
        ticketBuy.setTicketBuyingRequest(buyingRequest);
        return ticketBuy;
    }

}
