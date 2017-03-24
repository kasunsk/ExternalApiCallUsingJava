package com.crossover.techtrial.java.se.logic.airline;

import com.crossover.techtrial.java.se.common.dto.ServiceRequest;
import com.crossover.techtrial.java.se.common.dto.ServiceResponse;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.dao.airline.AirlineDao;
import com.crossover.techtrial.java.se.dao.email.EmailDao;
import com.crossover.techtrial.java.se.model.email.EmailModel;
import com.crossover.techtrial.java.se.model.user.User;
import com.crossover.techtrial.java.se.model.user.UserTicket;
import com.crossover.techtrial.java.se.service.email.EmailService;
import com.crossover.techtrial.java.se.service.user.UserService;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class UserTicketEmailSendingLogicUnitTest {

    @InjectMocks
    UserTicketEmailSendingLogic logic = new UserTicketEmailSendingLogic();

    @Mock
    private EmailDao emailHibernateDao;

    @Mock
    private AirlineDao airlineHibernateDao;

    @Mock
    private UserService userService;

    @Mock
    private EmailService emailService;


    @BeforeMethod(alwaysRun = true)
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void invalidUserTicketIdTest() {
        logic.invoke(null);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void emailSendSuccessTest() {

        predefineFunctionality();
        ServiceResponse<User> userResponse = getUserServiceResponse();
        when(userService.loadUserById(Matchers.<ServiceRequest>any())).thenReturn(userResponse);
        Boolean result = logic.invoke("12");
        verify(emailService, times(1)).sendEmail(Matchers.<ServiceRequest>any());
        verify(emailHibernateDao, times(1)).saveEmailData(Matchers.<EmailModel>any());
        assertTrue(result);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void emailSendFailTest() {

        predefineFunctionality();
        ServiceResponse<User> userResponse = getUserServiceResponse();
        when(userService.loadUserById(Matchers.<ServiceRequest>any())).thenReturn(userResponse);
        when(emailService.sendEmail(Matchers.<ServiceRequest>any())).thenThrow(new ServiceRuntimeException("Error sending mail"));
        assertFalse(logic.invoke("12"));
        verify(emailHibernateDao, times(1)).saveEmailData(Matchers.<EmailModel>any());
    }

    private void predefineFunctionality() {

        UserTicket userTicket = getUserTicket(25L);
        when(airlineHibernateDao.loadUserTicketById(12L)).thenReturn(userTicket);
    }

    private ServiceResponse<User> getUserServiceResponse() {
        ServiceResponse<User> userResponse = new ServiceResponse<>();
        User user = new User();
        user.setEmail("test@gmail.com");
        userResponse.setPayload(user);
        return userResponse;
    }

    private UserTicket getUserTicket(Long userId) {
        UserTicket userTicket = new UserTicket();
        userTicket.setUserId(userId);
        return userTicket;
    }

}
