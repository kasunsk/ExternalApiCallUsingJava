package com.crossover.techtrial.java.se.dao.user;


import com.crossover.techtrial.java.se.common.dto.UserSearchCriteria;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.model.user.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

public class UserHibernateDaoUnitTest {

    @InjectMocks
    UserHibernateDao userDao = new UserHibernateDao();

    @Mock
    SessionFactory sessionFactory;

    @Mock
    Session session;

    @Mock
    Query query;

    @BeforeMethod(alwaysRun = true)
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllUsersTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from User")).thenReturn(query);
        List<User> users = new ArrayList<>();
        when(query.list()).thenReturn(users);
        List<User> usersResult = userDao.allUsers();
        assertEquals(usersResult, users);
    }

    @Test
    public void loadUserByEmailTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from User U where U.email=:email")).thenReturn(query);
        User user = new User();
        when(query.uniqueResult()).thenReturn(user);
        User userResult = userDao.loadUserByEmail("email@gmail.com");
        assertEquals(user, userResult);
    }

    @Test
    public void loadUserByIdTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from User U where U.userId=:userId")).thenReturn(query);
        User user = new User();
        when(query.uniqueResult()).thenReturn(user);
        User userResult = userDao.loadUserById("20");
        assertEquals(user, userResult);
    }

    @Test
    public void saveUserTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        User user = new User();
        userDao.saveUser(user);
        verify(session, times(1)).saveOrUpdate(user);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void removeFailTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from User U where U.userId=:userId")).thenReturn(query);
        when(query.uniqueResult()).thenReturn(null);
        userDao.remove("30");
        verify(session, times(0)).delete(any());
    }

    @Test
    public void userRemoveTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from User U where U.userId=:userId")).thenReturn(query);
        when(query.uniqueResult()).thenReturn(new User());
        userDao.remove("30");
        verify(session, times(1)).delete(any());
    }

    @Test
    public void searchUserByCriteriaTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from User ")).thenReturn(query);
        List<User> users = new ArrayList<>();
        when(query.list()).thenReturn(users);
        List<User> usersResult = userDao.searchUserByCriteria(null);
        assertEquals(users, usersResult);
    }

    @Test
    public void searchUserByCriteriaEmptyTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from User ")).thenReturn(query);
        List<User> users = new ArrayList<>();
        when(query.list()).thenReturn(users);
        List<User> usersResult = userDao.searchUserByCriteria(new UserSearchCriteria());
        assertEquals(users, usersResult);
    }

    @Test
    public void searchUserByCriteriaEmailTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from User where email=:email")).thenReturn(query);
        List<User> users = new ArrayList<>();
        when(query.list()).thenReturn(users);
        UserSearchCriteria searchCriteria = new UserSearchCriteria();
        searchCriteria.setEmail("email");
        List<User> usersResult = userDao.searchUserByCriteria(searchCriteria);
        assertEquals(users, usersResult);
    }

    @Test
    public void searchUserByCriteriaEmailUserIdTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from User where userId=:userId and email=:email")).thenReturn(query);
        List<User> users = new ArrayList<>();
        when(query.list()).thenReturn(users);
        UserSearchCriteria searchCriteria = new UserSearchCriteria();
        searchCriteria.setEmail("email");
        searchCriteria.setUserId(24L);
        List<User> usersResult = userDao.searchUserByCriteria(searchCriteria);
        assertEquals(users, usersResult);
    }

    @Test
    public void searchUserByCriteriaEmailUserINameTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from User where userId=:userId and email=:email and name=:name")).thenReturn(query);
        List<User> users = new ArrayList<>();
        when(query.list()).thenReturn(users);
        UserSearchCriteria searchCriteria = new UserSearchCriteria();
        searchCriteria.setEmail("email");
        searchCriteria.setUserId(24L);
        searchCriteria.setName("test");
        List<User> usersResult = userDao.searchUserByCriteria(searchCriteria);
        assertEquals(users, usersResult);
    }

    @Test
    public void searchUserByCriteriaUserITest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from User where userId=:userId")).thenReturn(query);
        List<User> users = new ArrayList<>();
        when(query.list()).thenReturn(users);
        UserSearchCriteria searchCriteria = new UserSearchCriteria();
        searchCriteria.setUserId(24L);
        List<User> usersResult = userDao.searchUserByCriteria(searchCriteria);
        assertEquals(users, usersResult);
    }

    @Test
    public void searchUserByCriteriaUserINameTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from User where userId=:userId and name=:name")).thenReturn(query);
        List<User> users = new ArrayList<>();
        when(query.list()).thenReturn(users);
        UserSearchCriteria searchCriteria = new UserSearchCriteria();
        searchCriteria.setUserId(24L);
        searchCriteria.setName("test");
        List<User> usersResult = userDao.searchUserByCriteria(searchCriteria);
        assertEquals(users, usersResult);
    }

    @Test
    public void searchUserByCriteriaNameTest() {

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from User where name=:name")).thenReturn(query);
        List<User> users = new ArrayList<>();
        when(query.list()).thenReturn(users);
        UserSearchCriteria searchCriteria = new UserSearchCriteria();
        searchCriteria.setName("test");
        List<User> usersResult = userDao.searchUserByCriteria(searchCriteria);
        assertEquals(users, usersResult);
    }
}
