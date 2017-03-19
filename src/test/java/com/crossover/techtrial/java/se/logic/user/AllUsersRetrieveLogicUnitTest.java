package com.crossover.techtrial.java.se.logic.user;


import com.crossover.techtrial.java.se.common.dto.Void;
import com.crossover.techtrial.java.se.dao.user.UserDao;
import com.crossover.techtrial.java.se.model.user.User;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class AllUsersRetrieveLogicUnitTest {

    @InjectMocks
    AllUsersRetrieveLogic logic = new AllUsersRetrieveLogic();

    @Mock
    private UserDao userHibernateDao;

    @BeforeMethod(alwaysRun = true)
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void invokeTest() {

        List<User> users = new ArrayList<>();
        User user = new User();
        users.add(user);
        when(userHibernateDao.allUsers()).thenReturn(users);
        assertEquals(logic.invoke(new Void()), users);
    }

}
