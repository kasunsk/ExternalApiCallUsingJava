package com.crossover.techtrial.java.se.logic.account;

import com.crossover.techtrial.java.se.common.dto.Currency;
import com.crossover.techtrial.java.se.common.dto.CurrencyExchangeRequest;
import com.crossover.techtrial.java.se.common.dto.Price;
import com.crossover.techtrial.java.se.common.dto.ServiceRequest;
import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.dao.account.AccountDao;
import com.crossover.techtrial.java.se.model.account.BankAccount;
import com.crossover.techtrial.java.se.service.account.AccountService;
import com.crossover.techtrial.java.se.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.DoubleSummaryStatistics;

/**
 * This class is responsible for providing all the logic for bank account creation.
 */
@Component
public class AccountCreateLogic extends StatelessServiceLogic<BankAccount, BankAccount> {

    @Autowired
    private AccountDao accountHibernateDao;

    @Autowired
    private Environment environment;

    @Autowired
    private AccountService accountService;

    @Transactional
    @Override
    public BankAccount invoke(BankAccount bankAccount) {

        validateAccount(bankAccount);
        depositInitialAmount(bankAccount);
        BankAccount account = accountHibernateDao.createAccount(bankAccount);

        if (account.getUser() != null) {
            account.getUser().setUserBankAccounts(null);
        }
        return account;
    }

    private void depositInitialAmount(BankAccount bankAccount) {

        if (bankAccount.getAvailableAmount() == null || bankAccount.getAvailableAmount() == 0D) {

            String initialPaypalletsAmount = environment.getRequiredProperty("initial.deposit.amount");
            String initialCurrency = environment.getRequiredProperty("initial.deposit.currency");

            Double initialDepositAmount;

            if (!bankAccount.getCurrency().toString().equals(initialCurrency)) {

                CurrencyExchangeRequest exchangeRequest = new CurrencyExchangeRequest();
                exchangeRequest.setTargetCurrency(bankAccount.getCurrency());
                Price price = new Price();
                price.setPrice(Double.parseDouble(initialPaypalletsAmount));
                price.setCurrency(Currency.valueOf(initialCurrency));
                exchangeRequest.setMonetaryAmount(price);
                Price convertedPrice = accountService.exchangeCurrency(new ServiceRequest<>(exchangeRequest)).getPayload();
                initialDepositAmount = convertedPrice.getPrice();

            } else {
                initialDepositAmount = Double.parseDouble(initialPaypalletsAmount);
            }
            bankAccount.setAvailableAmount(initialDepositAmount);
        }
    }

    private void validateAccount(BankAccount bankAccount) {

        ValidationUtil.validate(bankAccount, "Bank account is null");
        ValidationUtil.validate(bankAccount.getCurrency(), "Currency is null");

        if (bankAccount.getAvailableAmount() == null) {
            bankAccount.setAvailableAmount(0D);
        }
    }

}
