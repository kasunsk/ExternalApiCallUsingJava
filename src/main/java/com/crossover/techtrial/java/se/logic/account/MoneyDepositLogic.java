package com.crossover.techtrial.java.se.logic.account;

import com.crossover.techtrial.java.se.common.execption.ErrorCode;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.configuration.ApplicationProperties;
import com.crossover.techtrial.java.se.dto.account.Account;
import com.crossover.techtrial.java.se.dto.account.MoneyTransferRequest;
import com.crossover.techtrial.java.se.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MoneyDepositLogic extends StatelessServiceLogic<Account, MoneyTransferRequest> {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Override
    public Account invoke(MoneyTransferRequest depositRequest) {

        validate(depositRequest);
        String depositUrl = applicationProperties.getBaseAPIUrl() + applicationProperties.getApplicantId()
                + "/paypallets/account/deposit";

        ResponseEntity<Account> response = restTemplate.postForEntity(depositUrl, depositRequest, Account.class);

        if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            throw new ServiceRuntimeException(ErrorCode.AMOUNT_ACCOUNT_EXCHANGE_RATE_NOT_FOUND,
                    "Applicant Amount or Account or Exchange range not found");
        } else if (response.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
            throw new ServiceRuntimeException(ErrorCode.AMOUNT_OR_CURRENCY, "Amount or Currency not valid");
        }
        return response.getBody();
    }

    private void validate(MoneyTransferRequest depositRequest) {

        ValidationUtil.validate(depositRequest, "Deposit request is null");
        ValidationUtil.validate(depositRequest.getAccountId(), "Account id is null");
        ValidationUtil.validate(depositRequest.getMonetaryAmount(), "Price is null");
        ValidationUtil.validate(depositRequest.getMonetaryAmount().getCurrency(), "Currency is null");
        ValidationUtil.validate(depositRequest.getMonetaryAmount().getAmount(), "Price amount is null");
    }

}
