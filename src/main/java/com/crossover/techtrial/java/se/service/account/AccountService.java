package com.crossover.techtrial.java.se.service.account;

import com.crossover.techtrial.java.se.common.dto.*;
import com.crossover.techtrial.java.se.dto.account.MoneyTransferRequest;
import com.crossover.techtrial.java.se.dto.account.Account;
import com.crossover.techtrial.java.se.dto.account.AccountCreateCriteria;

import java.util.List;

/**
 * This API provide all the service method for Account Service
 */
public interface AccountService {

    /**
     *
     * @param accountCreateCriteria
     * @return Created BankAccount
     */
    ServiceResponse<Account> createAccount(ServiceRequest<AccountCreateCriteria> accountCreateCriteria);

    /**
     *
     * @param depositRequest
     * @return updated BankAccount
     */
    ServiceResponse<Account> deposit(ServiceRequest<MoneyTransferRequest> depositRequest);

    /**
     *
     * @param applicantId
     * @return List of all BankAccount of applicant
     */
    ServiceResponse<List<Account>> loadAllAccounts(ServiceRequest<String> applicantId);


}
