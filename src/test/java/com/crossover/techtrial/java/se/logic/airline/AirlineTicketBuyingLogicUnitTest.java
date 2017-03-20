package com.crossover.techtrial.java.se.logic.airline;


import com.crossover.techtrial.java.se.common.dto.Currency;
import com.crossover.techtrial.java.se.common.dto.Price;
import com.crossover.techtrial.java.se.common.dto.ServiceRequest;
import com.crossover.techtrial.java.se.common.dto.ServiceResponse;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.dao.account.AccountDao;
import com.crossover.techtrial.java.se.dao.airline.AirlineDao;
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
    private AccountService accountService;

    @Mock
    private AirlineOfferLogicHelper helper;

    @Mock
    private AirlineDao airlineDao;

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
//        buyingRequest.setAirlineRout(new Route());
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
    public void inventoryAvailabilityTest() {
        TicketBuy ticketBuy = getTicketBuy();
        when(accountDao.loadAccountById(12L)).thenReturn(new BankAccount());
        AirlineOfferModel airlineOfferModel = new AirlineOfferModel();
        airlineOfferModel.setAvailbaleInventory(2);
        when(helper.loadOfferByRout(Matchers.<Route>any())).thenReturn(airlineOfferModel);
        logic.invoke(ticketBuy);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void creditAvailabilityTest() {
        TicketBuy ticketBuy = getTicketBuy();
        BankAccount bankAccount = getBankAccount(Currency.AUD, 100D);
        when(accountDao.loadAccountById(12L)).thenReturn(bankAccount);
        AirlineOfferModel airlineOfferModel = getAirlineOfferModel(20, Currency.AUD, 200D);
        when(helper.loadOfferByRout(Matchers.<Route>any())).thenReturn(airlineOfferModel);
        logic.invoke(ticketBuy);
        verify(accountService, times(0)).exchangeCurrency(any());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void invokeTest() {
        TicketBuy ticketBuy = getTicketBuy();
        BankAccount bankAccount = getBankAccount(Currency.AUD, 1000D);
        when(accountDao.loadAccountById(12L)).thenReturn(bankAccount);
        AirlineOfferModel airlineOfferModel = getAirlineOfferModel(20, Currency.USD, 200D);
        when(helper.loadOfferByRout(Matchers.<Route>any())).thenReturn(airlineOfferModel);

        ServiceResponse<Price> exchangedPriceResponse = getPriceServiceResponse();
        when(accountService.exchangeCurrency(Matchers.<ServiceRequest>any())).thenReturn(exchangedPriceResponse);
//        UserTicket userTicket = logic.invoke(ticketBuy);
        verify(accountService, times(1)).exchangeCurrency(any());
        verify(accountDao, times(1)).updateAccount(any());
        verify(airlineDao, times(1)).updateAirlineOffer(any());
        assertEquals(airlineOfferModel.getAvailbaleInventory(), new Integer(17));
        // For three passengers 250 * 3 = 750 ,Then 1000 - 750 = 250
        //assertEquals(bankAccount.getAvailableAmount(), 250D);
//        assertNotNull(userTicket);
    }

    private ServiceResponse<Price> getPriceServiceResponse() {
        ServiceResponse<Price> exchangedPriceResponse = new ServiceResponse<>();
        Price exchangedPrice = new Price();
        exchangedPrice.setCurrency(Currency.AUD);
        exchangedPrice.setPrice(250D);
        exchangedPriceResponse.setPayload(exchangedPrice);
        return exchangedPriceResponse;
    }

    private AirlineOfferModel getAirlineOfferModel(Integer availableInv, Currency currency, Double price) {
        AirlineOfferModel airlineOfferModel = new AirlineOfferModel();
        airlineOfferModel.setAvailbaleInventory(availableInv);
        airlineOfferModel.setCurrency(currency);
        airlineOfferModel.setPrice(price);
        return airlineOfferModel;
    }

    private BankAccount getBankAccount(Currency currency, Double availableAmount) {
        BankAccount bankAccount = new BankAccount();
        //bankAccount.setAvailableAmount(availableAmount);
        //bankAccount.setCurrency(currency);
        return bankAccount;
    }

    private TicketBuy getTicketBuy() {
        TicketBuy ticketBuy = new TicketBuy();
        ticketBuy.setUserId("5");
        TicketBuyingRequest buyingRequest = new TicketBuyingRequest();
        buyingRequest.setAccountId("12");
//        buyingRequest.setAirlineRout(new Route());
//        buyingRequest.setTicketAmount(3);
        ticketBuy.setTicketBuyingRequest(buyingRequest);
        return ticketBuy;
    }

}
