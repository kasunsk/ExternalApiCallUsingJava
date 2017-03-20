package com.crossover.techtrial.java.se.logic.account;

import com.crossover.techtrial.java.se.common.dto.Currency;
import com.crossover.techtrial.java.se.common.dto.CurrencyExchangeRequest;
import com.crossover.techtrial.java.se.common.dto.Price;
import com.crossover.techtrial.java.se.common.dto.ServiceRequest;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.configuration.ApplicationProperties;
import com.crossover.techtrial.java.se.dao.account.AccountDao;
import com.crossover.techtrial.java.se.dao.user.UserDao;
import com.crossover.techtrial.java.se.dto.account.AccountRequest;
import com.crossover.techtrial.java.se.model.account.BankAccount;
import com.crossover.techtrial.java.se.model.user.User;
import com.crossover.techtrial.java.se.service.account.AccountService;
import com.crossover.techtrial.java.se.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.DoubleSummaryStatistics;

import static com.crossover.techtrial.java.se.util.ValidationUtil.validate;

/**
 * This class is responsible for providing all the logic for bank account creation.
 */
@Component
public class AccountCreateLogic extends StatelessServiceLogic<Account, AccountCreateCriteria> {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    RestTemplate restTemplate;

    @Transactional
    @Override
    public Account invoke(AccountCreateCriteria criteria) {

        validateCriteria(criteria);
        String accountCreateUrl
                = applicationProperties.getBaseAPIUrl() + applicationProperties.getApplicantId() + "/paypallets/account";

        ResponseEntity<Account> response = restTemplate.postForEntity(accountCreateUrl, criteria.getAccountRequest(), Account.class);


        if (response.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
            throw new ServiceRuntimeException("Bad Request");
        } else if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)){
            throw new ServiceRuntimeException("Not Found");
        }

        Account account = response.getBody();
        BankAccount bankAccount = new BankAccount();
        User user =  userDao.loadUserById(criteria.getUserId());
        bankAccount.setUser(user);
        bankAccount.setAccountNumber(account.getId());
        accountDao.createAccount(bankAccount);
        return account;
    }

    private void validateCriteria(AccountCreateCriteria criteria) {
        validate(criteria, "Account create criteria is null");
        validate(criteria.getUserId(), "User id is null");
        validate(criteria.getAccountRequest(), "Account request is null");
        validate(criteria.getAccountRequest().getCurrency(), "Currency is null");
    }

}
