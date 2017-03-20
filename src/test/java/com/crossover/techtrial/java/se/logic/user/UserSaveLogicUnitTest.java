package com.crossover.techtrial.java.se.logic.user;

import com.crossover.techtrial.java.se.common.dto.Currency;
import com.crossover.techtrial.java.se.common.dto.ServiceRequest;
import com.crossover.techtrial.java.se.common.dto.ServiceResponse;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.configuration.ApplicationProperties;
import com.crossover.techtrial.java.se.dao.user.UserDao;
import com.crossover.techtrial.java.se.dto.user.UserRole;
import com.crossover.techtrial.java.se.dto.account.Account;
import com.crossover.techtrial.java.se.model.user.User;
import com.crossover.techtrial.java.se.service.account.AccountService;
import com.crossover.techtrial.java.se.service.security.SecurityService;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;


public class UserSaveLogicUnitTest {

    @InjectMocks
    UserSaveLogic userSaveLogic = new UserSaveLogic();

    @Mock
    UserDao userDao;

    @Mock
    SecurityService securityService;

    @Mock
    ApplicationProperties applicationProperties;

    @Mock
    RestTemplate restTemplate;

    @Mock
    AccountService accountService;

    @BeforeMethod(alwaysRun = true)
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void userValidateNullTest() throws Exception {
        userSaveLogic.validateUser(null);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void userEmailValidateTest() throws Exception {
        User user = new User();
        userSaveLogic.validateUser(user);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void userNameValidateTest() throws Exception {
        User user = new User();
        user.setEmail("test@email.com");
        userSaveLogic.validateUser(user);
    }


    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void userPasswordValidateTest() throws Exception {
        User user = new User();
        user.setEmail("test@email.com");
        user.setName("name");
        userSaveLogic.validateUser(user);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void userAlreadyExistValidateTest() throws Exception {
        User user = getCompletedUser();
        user.setRole(UserRole.ADMIN);

        when(userDao.loadUserByEmail("test@email.com")).thenReturn(new User());
        userSaveLogic.validateUser(user);
    }


    @Test
    public void userAlreadyExistValidateSuccessTest() throws Exception {
        User user = getCompletedUser();
        when(userDao.loadUserByEmail("test@email.com")).thenReturn(null);

        try {
            userSaveLogic.validateUser(user);
        } catch (ServiceRuntimeException ex) {
            fail();
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void encryptUserPasswordTest() {
        User user = new User();
        user.setPassword("test");
        ServiceResponse<String> response = new ServiceResponse<>();
        response.setPayload("encryptedTest");
        when(applicationProperties.getInitialDepositAmount()).thenReturn(1000D);
        when(applicationProperties.getInitialDepositCurrency()).thenReturn(Currency.USD);
        when(securityService.encrypt(Matchers.any(ServiceRequest.class))).thenReturn(response);
        userSaveLogic.encryptUserPassword(user);
        assertEquals(user.getPassword(), "encryptedTest");
    }

    private User getCompletedUser() {
        User user = new User();
        user.setEmail("test@email.com");
        user.setName("name");
        user.setPassword("password");
        user.setUserId(12L);
        return user;
    }

    @SuppressWarnings("unchecked")
    @Test
    public void invokeTest() {
        User user = getCompletedUser();
        when(userDao.loadUserByEmail("test@email.com")).thenReturn(null);
        ServiceResponse<String> response = new ServiceResponse<>();
        response.setPayload("encryptedTest");
        when(applicationProperties.getInitialDepositAmount()).thenReturn(1000D);
        when(applicationProperties.getInitialDepositCurrency()).thenReturn(Currency.USD);
        when(securityService.encrypt(Matchers.any(ServiceRequest.class))).thenReturn(response);
        ServiceResponse<Account> accountResponse = getAccountServiceResponse();
        when(accountService.createAccount(Matchers.<ServiceRequest>any())).thenReturn(accountResponse);
        String resultName = userSaveLogic.invoke(user);
        assertEquals("name", resultName);
    }

    private ServiceResponse<Account> getAccountServiceResponse() {
        ServiceResponse<Account> accountResponse = new ServiceResponse<>();
        Account createdAccount = new Account();
        accountResponse.setPayload(createdAccount);
        return accountResponse;
    }
}
