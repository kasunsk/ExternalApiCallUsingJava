package com.crossover.techtrial.java.se.logic.account;

import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.configuration.ApplicationProperties;
import com.crossover.techtrial.java.se.dao.account.AccountDao;
import com.crossover.techtrial.java.se.dto.account.Account;
import com.crossover.techtrial.java.se.dto.account.DepositRequest;
import com.crossover.techtrial.java.se.dto.account.MoneyTransferRequest;
import com.crossover.techtrial.java.se.model.account.BankAccount;
import com.crossover.techtrial.java.se.service.account.AccountService;
import com.crossover.techtrial.java.se.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MoneyDepositLogic extends StatelessServiceLogic<Account, MoneyTransferRequest> {

    @Autowired
    private AccountDao accountHibernateDao;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Override
    public Account invoke(MoneyTransferRequest depositRequest) {

        validate(depositRequest);
        String depositUrl = applicationProperties.getBaseAPIUrl() + applicationProperties.getApplicantId()
                + "/paypallets/account/deposit";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Account> response = restTemplate.postForEntity(depositUrl, depositRequest, Account.class);
        return response.getBody();
    }

    private void validate(MoneyTransferRequest depositRequest) {

        ValidationUtil.validate(depositRequest, "Deposit request is null");
        ValidationUtil.validate(depositRequest.getAccountId(), "Account id is null");
        ValidationUtil.validate(depositRequest.getMonetaryAmount(), "Price is null");
        ValidationUtil.validate(depositRequest.getMonetaryAmount().getCurrency(), "Currency is null");
        ValidationUtil.validate(depositRequest.getMonetaryAmount().getAmount(), "Price amount is null");
    }

    private double getNewBalance(DepositRequest depositRequest, BankAccount account) {

//        if (account == null) {
//            throw new ServiceRuntimeException(ErrorCode.ACCOUNT_NOT_EXIST, "Invalid Account");
//        }
//
//        Price depositPrice = depositRequest.getPrice();
//
//        //Provide currency exchange if necessary
//        if (!account.getCurrency().equals(depositPrice.getCurrency())) {
//            CurrencyExchangeRequest exchangeRequest = new CurrencyExchangeRequest();
//            exchangeRequest.setTargetCurrency(account.getCurrency());
//            exchangeRequest.setMonetaryAmount(depositPrice);
//            depositPrice = accountService.exchangeCurrency(new ServiceRequest<>(exchangeRequest)).getPayload();
//        }
//
//        BigDecimal newBalance = BigDecimal.valueOf(account.getAvailableAmount()).add(BigDecimal
//                .valueOf(depositPrice.getPrice()));
//
//        return newBalance.doubleValue();
        return 0D;
    }
}
