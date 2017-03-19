package com.crossover.techtrial.java.se.logic.account;


import com.crossover.techtrial.java.se.common.dto.Currency;
import com.crossover.techtrial.java.se.common.dto.CurrencyExchangeRequest;
import com.crossover.techtrial.java.se.common.dto.Price;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import org.mockito.InjectMocks;
import org.testng.annotations.Test;

//@PrepareForTest(CurrencyConverter.class)
public class CurrencyExchangeLogicUnitTest {

    @InjectMocks
    CurrencyExchangeLogic logic = new CurrencyExchangeLogic();

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void requestNullTest() {
        logic.invoke(null);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void priceNullTest() {
        logic.invoke(new CurrencyExchangeRequest());
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void targetCurrencyNullTest() {

        CurrencyExchangeRequest request = new CurrencyExchangeRequest();
        request.setMonetaryAmount(new Price());
        logic.invoke(request);
    }

    @Test
    public void currencyExchangeTest() {

        CurrencyExchangeRequest request = new CurrencyExchangeRequest();
        Price amount = new Price();
        amount.setCurrency(Currency.AUD);
        amount.setPrice(125D);
        request.setMonetaryAmount(amount);
        request.setTargetCurrency(Currency.USD);
        logic.invoke(request);
    }

}
