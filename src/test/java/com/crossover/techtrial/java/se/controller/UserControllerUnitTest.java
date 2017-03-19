package com.crossover.techtrial.java.se.controller;


import com.crossover.techtrial.java.se.common.dto.ServiceRequest;
import com.crossover.techtrial.java.se.common.dto.ServiceResponse;
import com.crossover.techtrial.java.se.common.dto.UserSearchCriteria;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.dto.user.LoginRequest;
import com.crossover.techtrial.java.se.dto.user.UserRole;
import com.crossover.techtrial.java.se.model.user.User;
import com.crossover.techtrial.java.se.service.user.UserService;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class UserControllerUnitTest {

    @InjectMocks
    UserController controller = new UserController();

    @Mock
    UserService userService;

    @Mock
    MessageSource messageSource;

    @BeforeMethod(alwaysRun = true)
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void removeUserTest() {
        controller.removeUser("test");
        verify(userService, times(1)).removeUser(Matchers.<ServiceRequest>any());
    }

    @SuppressWarnings("unchecked")
    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void searchUsersFailTest() {

        User user = new User();
        user.setRole(UserRole.USER);
        ServiceResponse<User> userServiceResponse = new ServiceResponse<>();
        userServiceResponse.setPayload(user);
        when(userService.loadUserById(Matchers.<ServiceRequest>any())).thenReturn(userServiceResponse);

        UserSearchCriteria searchCriteria = new UserSearchCriteria();
        controller.searchUser("12", searchCriteria);
        verify(userService, times(0)).searchUser(any());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void searchUserTest() {

        User user = new User();
        user.setRole(UserRole.ADMIN);
        ServiceResponse<User> userServiceResponse = new ServiceResponse<>();
        userServiceResponse.setPayload(user);
        when(userService.loadUserById(Matchers.<ServiceRequest>any())).thenReturn(userServiceResponse);

        ServiceResponse<List<User>> userListResponse = new ServiceResponse<>();
        List<User> users = new ArrayList<>();
        userListResponse.setPayload(users);
        when(userService.searchUser(Matchers.<ServiceRequest>any())).thenReturn(userListResponse);
        UserSearchCriteria searchCriteria = new UserSearchCriteria();
        assertEquals(users, controller.searchUser("12", searchCriteria));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void saveUserTest() {

        assertTrue(controller.saveUser(new User()));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void userLoginTest() {

        ServiceResponse<User> response = new ServiceResponse<>();
        User user = new User();
        response.setPayload(user);
        when(userService.login(Matchers.<ServiceRequest>any())).thenReturn(response);
        LoginRequest loginRequest = new LoginRequest();
        assertEquals(user, controller.login(loginRequest));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void allUsersTest() {

        ServiceResponse<List<User>> response = new ServiceResponse<>();
        List<User> users = new ArrayList<>();
        users.add(new User());
        response.setPayload(users);
        when(userService.retrieveAllUsers(Matchers.<ServiceRequest>any())).thenReturn(response);
        assertEquals(users, controller.allUsers());
    }


}
