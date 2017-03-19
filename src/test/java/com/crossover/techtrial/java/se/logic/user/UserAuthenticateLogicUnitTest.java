package com.crossover.techtrial.java.se.logic.user;

import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.dao.user.UserDao;
import com.crossover.techtrial.java.se.model.user.User;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;
import static org.testng.Assert.fail;

public class UserAuthenticateLogicUnitTest {

    @InjectMocks
    UserAuthenticateLogic logic = new UserAuthenticateLogic();

    @Mock
    UserDao userHibernateDao;

    @BeforeMethod(alwaysRun = true)
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void authenticateFailTest() {
        when(userHibernateDao.loadUserById("12")).thenReturn(null);
        logic.invoke("12");
    }

    @Test
    public void authenticateTest() {

        try {
            when(userHibernateDao.loadUserById("12")).thenReturn(new User());
            logic.invoke("12");
        } catch (ServiceRuntimeException ex) {
            fail();
        }
    }

}
