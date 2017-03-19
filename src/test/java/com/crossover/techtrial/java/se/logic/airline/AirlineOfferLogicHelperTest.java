package com.crossover.techtrial.java.se.logic.airline;


import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.dao.airline.AirlineDao;
import com.crossover.techtrial.java.se.model.airline.AirlineOfferModel;
import com.crossover.techtrial.java.se.model.airline.Route;
import com.crossover.techtrial.java.se.service.user.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.FileAssert.fail;


public class AirlineOfferLogicHelperTest {

    @InjectMocks
    AirlineOfferLogicHelper logicHelper = new AirlineOfferLogicHelper();

    @Mock
    AirlineDao airlineDao;

    @Mock
    UserService userService;

    @BeforeMethod(alwaysRun = true)
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void loadAirlineOfferByRouteInvalidInputTest() {
        logicHelper.loadOfferByRout(null);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void loadAirlineOfferByRouteInvalidInputDestinationTest() {
        logicHelper.loadOfferByRout(new Route());
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void loadAirlineOfferByRouteInvalidOriginTest() {
        Route route = getRoute(null, "XXX");
        logicHelper.loadOfferByRout(route);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void loadAirlineOfferByRouteInvalidDestinationTest() {
        Route route = getRoute("YYY", null);
        logicHelper.loadOfferByRout(route);
    }

    @Test
    public void loadAirlineOfferByRouteTest() {
        Route route = getRoute("YYY", "XXX");
        AirlineOfferModel airlineOfferModel = new AirlineOfferModel();
        when(airlineDao.loadOfferByRoute("YYY", "XXX")).thenReturn(airlineOfferModel);
        AirlineOfferModel resultAirlineOfferModel = logicHelper.loadOfferByRout(route);
        assertEquals(resultAirlineOfferModel, airlineOfferModel);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void authenticateApplicantInputValidateNullTest(){
        logicHelper.authenticateApplicant(null);
    }


    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void authenticateApplicantInputValidateEmptyTest(){
        logicHelper.authenticateApplicant("");
    }

    @Test
    public void authenticateApplicantInputValidateTest(){

        try {
            logicHelper.authenticateApplicant("applicantId");
        } catch (ServiceRuntimeException ex) {
            fail();
        }
    }

    private Route getRoute(String origin, String destination) {
        Route route = new Route();
        route.setTo(destination);
        route.setFrom(origin);
        return route;
    }
}
