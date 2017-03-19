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
import static org.testng.Assert.assertEquals;

public class UserLogicHelperUnitTest {

    @InjectMocks
    UserLogicHelper helper = new UserLogicHelper();

    @Mock
    UserDao userHibernateDao;

    @BeforeMethod(alwaysRun=true)
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void loadUserFailTest() {

        String email = "test@test.com";
        when(userHibernateDao.loadUserByEmail(email)).thenReturn(null);
        helper.loadUser(email);
    }

    @Test
    public void loadUserTest() {

        String email = "test@test.com";
        User expected = new User();
        when(userHibernateDao.loadUserByEmail(email)).thenReturn(expected);
        User result = helper.loadUser(email);
        assertEquals(result, expected);
    }
}
