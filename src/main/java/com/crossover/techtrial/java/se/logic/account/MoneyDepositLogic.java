package com.crossover.techtrial.java.se.logic.account;

import com.crossover.techtrial.java.se.common.dto.CurrencyExchangeRequest;
import com.crossover.techtrial.java.se.common.dto.Price;
import com.crossover.techtrial.java.se.common.dto.ServiceRequest;
import com.crossover.techtrial.java.se.common.execption.ErrorCode;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.dao.account.AccountDao;
import com.crossover.techtrial.java.se.dto.account.DepositRequest;
import com.crossover.techtrial.java.se.model.account.BankAccount;
import com.crossover.techtrial.java.se.service.account.AccountService;
import com.crossover.techtrial.java.se.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Component
public class MoneyDepositLogic extends StatelessServiceLogic<BankAccount, DepositRequest> {

    @Autowired
    private AccountDao accountHibernateDao;

    @Autowired
    private AccountService accountService;

    @Transactional
    @Override
    public BankAccount invoke(DepositRequest depositRequest) {

        validate(depositRequest);
        BankAccount account = accountHibernateDao.loadAccountById(Long.parseLong(depositRequest.getAccountId()));
        Double newBalance = getNewBalance(depositRequest, account);
        account.setAvailableAmount(newBalance);
        accountHibernateDao.updateAccount(account);
        return account;
    }

    private void validate(DepositRequest depositRequest) {

        ValidationUtil.validate(depositRequest, "Deposit request is null");
        ValidationUtil.validate(depositRequest.getAccountId(), "Account id is null");
        ValidationUtil.validate(depositRequest.getPrice(), "Price is null");
        ValidationUtil.validate(depositRequest.getPrice().getCurrency(), "Currency is null");
        ValidationUtil.validate(depositRequest.getPrice().getPrice(), "Price amount is null");
    }

    private double getNewBalance(DepositRequest depositRequest, BankAccount account) {

        if (account == null) {
            throw new ServiceRuntimeException(ErrorCode.ACCOUNT_NOT_EXIST, "Invalid Account");
        }

        Price depositPrice = depositRequest.getPrice();

        //Provide currency exchange if necessary
        if (!account.getCurrency().equals(depositPrice.getCurrency())) {
            CurrencyExchangeRequest exchangeRequest = new CurrencyExchangeRequest();
            exchangeRequest.setTargetCurrency(account.getCurrency());
            exchangeRequest.setMonetaryAmount(depositPrice);
            depositPrice = accountService.exchangeCurrency(new ServiceRequest<>(exchangeRequest)).getPayload();
        }

        BigDecimal newBalance = BigDecimal.valueOf(account.getAvailableAmount()).add(BigDecimal
                .valueOf(depositPrice.getPrice()));

        return newBalance.doubleValue();
    }
}
