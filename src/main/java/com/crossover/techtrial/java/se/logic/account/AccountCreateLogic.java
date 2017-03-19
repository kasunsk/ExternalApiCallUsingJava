package com.crossover.techtrial.java.se.logic.account;

import com.crossover.techtrial.java.se.common.dto.Currency;
import com.crossover.techtrial.java.se.common.dto.CurrencyExchangeRequest;
import com.crossover.techtrial.java.se.common.dto.Price;
import com.crossover.techtrial.java.se.common.dto.ServiceRequest;
import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.configuration.ApplicationProperties;
import com.crossover.techtrial.java.se.dao.account.AccountDao;
import com.crossover.techtrial.java.se.dto.account.AccountRequest;
import com.crossover.techtrial.java.se.model.account.BankAccount;
import com.crossover.techtrial.java.se.service.account.AccountService;
import com.crossover.techtrial.java.se.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.DoubleSummaryStatistics;

/**
 * This class is responsible for providing all the logic for bank account creation.
 */
@Component
public class AccountCreateLogic extends StatelessServiceLogic<Account, AccountCreateCriteria> {

    @Autowired
    private AccountDao accountHibernateDao;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Transactional
    @Override
    public Account invoke(AccountCreateCriteria criteria) {

        RestTemplate restTemplate = new RestTemplate();

        String accountCreateUrl
                = applicationProperties.getBaseAPIUrl() + applicationProperties.getApplicantId() + "/paypallets/account";

        ResponseEntity<Account> response = restTemplate.postForEntity(accountCreateUrl, criteria.getAccountRequest(), Account.class);
        Account account = response.getBody();

        BankAccount bankAccount = new BankAccount();
        bankAccount.setUser(criteria.getUser());
        bankAccount.setAccountNumber(account.getId());
        accountHibernateDao.createAccount(bankAccount);
        return account;
    }

    private void depositInitialAmount(BankAccount bankAccount) {

//        if (bankAccount.getAvailableAmount() == null || bankAccount.getAvailableAmount() == 0D) {
//
//            String initialPaypalletsAmount = environment.getRequiredProperty("initial.deposit.amount");
//            String initialCurrency = environment.getRequiredProperty("initial.deposit.currency");
//
//            Double initialDepositAmount;
//
//            if (!bankAccount.getCurrency().toString().equals(initialCurrency)) {
//
//                CurrencyExchangeRequest exchangeRequest = new CurrencyExchangeRequest();
//                exchangeRequest.setTargetCurrency(bankAccount.getCurrency());
//                Price price = new Price();
//                price.setPrice(Double.parseDouble(initialPaypalletsAmount));
//                price.setCurrency(Currency.valueOf(initialCurrency));
//                exchangeRequest.setMonetaryAmount(price);
//                Price convertedPrice = accountService.exchangeCurrency(new ServiceRequest<>(exchangeRequest)).getPayload();
//                initialDepositAmount = convertedPrice.getPrice();
//
//            } else {
//                initialDepositAmount = Double.parseDouble(initialPaypalletsAmount);
//            }
//            bankAccount.setAvailableAmount(initialDepositAmount);
//        }
    }

    private void validateAccount(BankAccount bankAccount) {

//        ValidationUtil.validate(bankAccount, "Bank account is null");
//        ValidationUtil.validate(bankAccount.getCurrency(), "Currency is null");
//
//        if (bankAccount.getAvailableAmount() == null) {
//            bankAccount.setAvailableAmount(0D);
//        }
    }

}
