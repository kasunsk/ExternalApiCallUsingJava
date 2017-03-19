package com.crossover.techtrial.java.se.logic.user;

import com.crossover.techtrial.java.se.common.dto.Currency;
import com.crossover.techtrial.java.se.common.dto.Price;
import com.crossover.techtrial.java.se.common.dto.ServiceRequest;
import com.crossover.techtrial.java.se.common.execption.ErrorCode;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.dao.user.UserDao;
import com.crossover.techtrial.java.se.dto.user.UserRole;
import com.crossover.techtrial.java.se.model.account.BankAccount;
import com.crossover.techtrial.java.se.model.user.User;
import com.crossover.techtrial.java.se.service.account.AccountService;
import com.crossover.techtrial.java.se.service.security.SecurityService;
import com.crossover.techtrial.java.se.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;

@Component
public class UserSaveLogic extends StatelessServiceLogic<String, User> {

    @Autowired
    private UserDao userHibernateDao;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private Environment environment;

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

        String initialPaypalletsAmount = environment.getRequiredProperty("initial.deposit.amount");
        String initialCurrency = environment.getRequiredProperty("initial.deposit.currency");

        BankAccount bankAccount = new BankAccount();
        bankAccount.setAvailableAmount(Double.parseDouble(initialPaypalletsAmount));
        bankAccount.setCurrency(Currency.valueOf(initialCurrency));
        bankAccount.setUser(user);

        accountService.createAccount(new ServiceRequest<>(bankAccount));
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
