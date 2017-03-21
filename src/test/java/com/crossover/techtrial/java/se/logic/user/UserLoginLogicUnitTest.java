package com.crossover.techtrial.java.se.logic.user;


import com.crossover.techtrial.java.se.common.dto.ServiceRequest;
import com.crossover.techtrial.java.se.common.dto.ServiceResponse;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.dto.user.LoginRequest;
import com.crossover.techtrial.java.se.model.user.User;
import com.crossover.techtrial.java.se.service.security.SecurityService;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class UserLoginLogicUnitTest {

    @InjectMocks
    UserLoginLogic logic = new UserLoginLogic();

    @Mock
    private UserLogicHelper logicHelper;

    @Mock
    private SecurityService securityService;

    @BeforeMethod(alwaysRun = true)
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void loginRequestNullTest() {
        logic.invoke(null);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void emailNullTest() {
        logic.invoke(new LoginRequest());
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void passwordNullTest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("email");
        logic.invoke(loginRequest);
    }

    @SuppressWarnings("unchecked")
    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void invalidLoginTest() {

        User user = new User();
        user.setPassword("xxx");
        when(logicHelper.loadUserByEmail("email")).thenReturn(user);

        ServiceResponse<String> encryptResponse = new ServiceResponse<>();
        encryptResponse.setPayload("aaaa");
        when(securityService.encrypt(Matchers.<ServiceRequest>any())).thenReturn(encryptResponse);

        LoginRequest loginRequest = getLoginRequest();
        logic.invoke(loginRequest);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void loginSuccessTest() {

        User user = new User();
        user.setPassword("xxx");
        when(logicHelper.loadUserByEmail("email")).thenReturn(user);

        ServiceResponse<String> encryptResponse = new ServiceResponse<>();
        encryptResponse.setPayload("xxx");
        when(securityService.encrypt(Matchers.<ServiceRequest>any())).thenReturn(encryptResponse);

        LoginRequest loginRequest = getLoginRequest();
        assertEquals(user, logic.invoke(loginRequest));
    }

    private LoginRequest getLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("email");
        loginRequest.setPassword("password");
        return loginRequest;
    }
}
