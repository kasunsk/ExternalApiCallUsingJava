package com.crossover.techtrial.java.se.logic.email;


import com.crossover.techtrial.java.se.dto.email.EmailParam;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;

public class EmailSendLogicUnitTest {

    @InjectMocks
    EmailSendLogic logic = new EmailSendLogic();

    @Mock
    private Environment environment;

    @BeforeMethod(alwaysRun=true)
    public void init() {
        MockitoAnnotations.initMocks(this);
        when(environment.getRequiredProperty("email.sender.address")).thenReturn("airlineservicetest@gmail.com");
        when(environment.getRequiredProperty("email.sender.password")).thenReturn("Password@123");
        logic.init();
    }

    @Test
    public void mailSendingTest() {

        when(environment.getRequiredProperty("email.smtp.server")).thenReturn("smtp.gmail.com");
        when(environment.getRequiredProperty("email.server.port")).thenReturn("587");

        EmailParam param = new EmailParam();
        param.setContent("Please enjoy our service");
        param.setSubject("Ticket Details");
        param.setReceiverAddress("sunethefac@gmail.com");
        logic.invoke(param);
    }

}
