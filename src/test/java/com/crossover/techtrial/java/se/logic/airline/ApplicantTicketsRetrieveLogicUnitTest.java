package com.crossover.techtrial.java.se.logic.airline;


import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.dao.airline.AirlineDao;
import com.crossover.techtrial.java.se.model.user.UserTicket;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class ApplicantTicketsRetrieveLogicUnitTest {

    @InjectMocks
    ApplicantTicketsRetrieveLogic logic = new ApplicantTicketsRetrieveLogic();

    @Mock
    private AirlineDao airlineDao;

    @BeforeMethod(alwaysRun = true)
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void retrieveFailTest() {
        logic.invoke(null);
    }

    @Test
    public void retrieveTest() {

        List<UserTicket> userTickets = new ArrayList<>();
        when(airlineDao.loadApplicantAirlineOffers(12L)).thenReturn(userTickets);
        List<UserTicket> result = logic.invoke("12");
        assertEquals(result, userTickets);
    }
}
