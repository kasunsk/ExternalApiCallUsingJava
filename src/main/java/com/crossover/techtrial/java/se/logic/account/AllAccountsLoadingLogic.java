package com.crossover.techtrial.java.se.logic.account;

import com.crossover.techtrial.java.se.common.execption.ErrorCode;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.configuration.ApplicationProperties;
import com.crossover.techtrial.java.se.dao.account.AccountDao;
import com.crossover.techtrial.java.se.dto.account.Account;
import com.crossover.techtrial.java.se.model.account.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class AllAccountsLoadingLogic extends StatelessServiceLogic<List<Account>, String> {

    @Autowired
    private AccountDao accountDao;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Transactional
    @Override
    public List<Account> invoke(String userId) {

        validateInput(userId);

        String availableOfferUrl
                = applicationProperties.getBaseAPIUrl() + applicationProperties.getApplicantId() + "/paypallets/accounts";

        ResponseEntity<Account[]> responseEntity = restTemplate.getForEntity(availableOfferUrl, Account[].class);
        List<Account> accounts = Arrays.asList(responseEntity.getBody());

        List<BankAccount> bankAccounts = accountDao.loadAccountByApplicantId(userId);
        return filterUserAccounts(accounts, bankAccounts);
    }

    private List<Account> filterUserAccounts(List<Account> accounts, List<BankAccount> bankAccounts) {

        List<Account> filteredAccounts = new ArrayList<>();
        List<String> accountNumbers = getAccountNumbers(bankAccounts);

        for (Account account : accounts) {
            if (accountNumbers.contains(account.getId())) {
                filteredAccounts.add(account);
            }
        }
        return filteredAccounts;
    }

    private List<String> getAccountNumbers(List<BankAccount> bankAccounts) {

        List<String> accountNumbers = new ArrayList<>();

        for (BankAccount bankAccount : bankAccounts) {
            accountNumbers.add(bankAccount.getAccountNumber());
        }
        return accountNumbers;
    }

    private void validateInput(String applicantId) {

        if (applicantId == null || applicantId.isEmpty()) {
            throw new ServiceRuntimeException(ErrorCode.INVALID_INPUT, "Applicant id is null");
        }
    }
}
