package com.crossover.techtrial.java.se.logic.airline;

import com.crossover.techtrial.java.se.adapter.airline.AirlineOfferAdapter;
import com.crossover.techtrial.java.se.common.dto.Price;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.dao.airline.AirlineDao;
import com.crossover.techtrial.java.se.dto.airline.AirlineOffer;
import com.crossover.techtrial.java.se.model.airline.AirlineOfferModel;
import com.crossover.techtrial.java.se.model.airline.Route;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;
import static org.testng.FileAssert.fail;

/**
 * This class will provide airline offer creation logic component unit test
 */
public class AirlineOfferCreateLogicUnitTest {

    @InjectMocks
    AirlineOfferCreateLogic logic = new AirlineOfferCreateLogic();

    @Mock
    AirlineOfferLogicHelper helper;

    @Mock
    AirlineDao airlineDao;

    @Mock
    AirlineOfferAdapter offerAdapter;

    @BeforeMethod(alwaysRun = true)
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void validateAirlineOfferNullTest() {
        logic.invoke(null);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void validateAirlineOfferPriceNullTest() {
        logic.invoke(new AirlineOffer());
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void validateAirlineOfferRouteNullTest() {
        AirlineOffer airlineOffer = new AirlineOffer();
        airlineOffer.setPrice(new Price());
        logic.invoke(airlineOffer);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void validateAirlineOfferRouteValidateTest() {
        AirlineOffer airlineOffer = new AirlineOffer();
        airlineOffer.setPrice(new Price());
        Route airlineRoute = getRoute("AAA", "AAA");
        airlineOffer.setRoute(airlineRoute);
        logic.invoke(airlineOffer);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void validateAirlineOfferRouteAlreadyExistTest() {
        AirlineOffer airlineOffer = new AirlineOffer();
        airlineOffer.setPrice(new Price());
        Route airlineRoute = getRoute("AAA","BBB");
        airlineOffer.setRoute(airlineRoute);

        when(helper.loadOfferByRout(airlineRoute)).thenReturn(new AirlineOfferModel());
        logic.invoke(airlineOffer);
    }

    @Test
    public void invokeTest() {
        AirlineOffer airlineOffer = new AirlineOffer();
        airlineOffer.setPrice(new Price());
        Route airlineRoute = getRoute("AAA","BBB");
        airlineOffer.setRoute(airlineRoute);

        when(helper.loadOfferByRout(airlineRoute)).thenReturn(null);
        when(offerAdapter.adaptFromDto(airlineOffer)).thenReturn(new AirlineOfferModel());

        try {
            logic.invoke(airlineOffer);
        } catch (ServiceRuntimeException ex) {
            fail();
        }
    }

    private Route getRoute(String origin, String destination) {
        Route airlineRoute = new Route();
        airlineRoute.setFrom(origin);
        airlineRoute.setTo(destination);
        return airlineRoute;
    }
}
