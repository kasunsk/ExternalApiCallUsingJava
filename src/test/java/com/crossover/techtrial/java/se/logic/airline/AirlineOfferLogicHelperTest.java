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

}
