package com.crossover.techtrial.java.se.logic.user;

import com.crossover.techtrial.java.se.common.dto.Currency;
import com.crossover.techtrial.java.se.common.dto.ServiceRequest;
import com.crossover.techtrial.java.se.common.execption.ErrorCode;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.configuration.ApplicationProperties;
import com.crossover.techtrial.java.se.dao.account.AccountDao;
import com.crossover.techtrial.java.se.dao.user.UserDao;
import com.crossover.techtrial.java.se.dto.account.AccountRequest;
import com.crossover.techtrial.java.se.dto.user.UserRole;
import com.crossover.techtrial.java.se.logic.account.Account;
import com.crossover.techtrial.java.se.logic.account.AccountCreateCriteria;
import com.crossover.techtrial.java.se.model.account.BankAccount;
import com.crossover.techtrial.java.se.model.user.User;
import com.crossover.techtrial.java.se.service.account.AccountService;
import com.crossover.techtrial.java.se.service.security.SecurityService;
import com.crossover.techtrial.java.se.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Arrays;

@Component
public class UserSaveLogic extends StatelessServiceLogic<String, User> {

    @Autowired
    private UserDao userHibernateDao;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Transactional
    @Override
    public String invoke(User user) {
        validateUser(user);
        encryptUserPassword(user);
        createInitialAccountForUser(user);
        userHibernateDao.saveUser(user);
        return user.getName();
    }

    private void createInitialAccountForUser(User user) {
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setCurrency(Currency.USD);
        AccountCreateCriteria createCriteria = new AccountCreateCriteria();
        createCriteria.setAccountRequest(accountRequest);
        createCriteria.setUser(user);
        accountService.createAccount(new ServiceRequest<>(createCriteria));
    }

    protected void encryptUserPassword(User user) {

        String encryptedPassword = securityService.encrypt(new ServiceRequest<>(user.getPassword())).getPayload();
        user.setPassword(encryptedPassword);
    }

    protected void validateUser(User user) {

        ValidationUtil.validate(user, "User can not be empty");
        ValidationUtil.validate(user.getEmail(), "Email can not be emplty");
        ValidationUtil.validate(user.getName(), "Name can not be empty");
        ValidationUtil.validate(user.getPassword(), "Password can not be empty");

        if (user.getRole() == null) {
            user.setRole(UserRole.USER);
        }

        User currentUser = userHibernateDao.loadUserByEmail(user.getEmail());

        if (currentUser != null) {
            throw new ServiceRuntimeException(ErrorCode.USER_ALREADY_EXIST, "User already exist");
        }
    }
}
