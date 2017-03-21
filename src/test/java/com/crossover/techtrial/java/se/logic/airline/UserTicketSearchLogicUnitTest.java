package com.crossover.techtrial.java.se.logic.airline;

import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.dao.airline.AirlineDao;
import com.crossover.techtrial.java.se.dto.user.UserTicketSearchCriteria;
import com.crossover.techtrial.java.se.logic.user.UserLogicHelper;
import com.crossover.techtrial.java.se.model.user.User;
import com.crossover.techtrial.java.se.model.user.UserTicket;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class UserTicketSearchLogicUnitTest {

    @InjectMocks
    UserTicketSearchLogic logic = new UserTicketSearchLogic();

    @Mock
    AirlineDao airlineDao;

    @Mock
    UserLogicHelper userLogicHelper;

    @BeforeMethod(alwaysRun = true)
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void invokeInputNullFailTest() {
        logic.invoke(null);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void invokeFailTest() {
        UserTicketSearchCriteria criteria = new UserTicketSearchCriteria();
        criteria.setEmail("email");
        when(userLogicHelper.loadUserByEmail("email")).thenThrow(new ServiceRuntimeException("User Not Found"));
        logic.invoke(criteria);
    }

    @Test
    public void invokeTest() {
        UserTicketSearchCriteria criteria = new UserTicketSearchCriteria();
        criteria.setEmail("email");
        User user = new User();
        user.setUserId(5L);
        when(userLogicHelper.loadUserByEmail("email")).thenReturn(user);
        List<UserTicket> userTickets = new ArrayList<>();
        when(airlineDao.searchUserTicket(5L, "email", null)).thenReturn(userTickets);
        assertEquals(logic.invoke(criteria), userTickets);
    }

    @Test
    public void invokeWithoutUserEmailTest() {
        UserTicketSearchCriteria criteria = new UserTicketSearchCriteria();
        List<UserTicket> userTickets = new ArrayList<>();
        when(airlineDao.searchUserTicket(null, null, null)).thenReturn(userTickets);
        assertEquals(logic.invoke(criteria), userTickets);
    }
}
