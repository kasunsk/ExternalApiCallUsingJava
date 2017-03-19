package com.crossover.techtrial.java.se.service.account;

import com.crossover.techtrial.java.se.common.dto.*;
import com.crossover.techtrial.java.se.common.dto.Void;
import com.crossover.techtrial.java.se.dto.account.DepositRequest;
import com.crossover.techtrial.java.se.dto.account.MoneyTransferRequest;
import com.crossover.techtrial.java.se.model.account.BankAccount;

import java.util.List;

/**
 * This API provide all the service method for Account Service
 */
public interface AccountService {

    /**
     *
     * @param bankAccount
     * @return Created BankAccount
     */
    ServiceResponse<BankAccount> createAccount(ServiceRequest<BankAccount> bankAccount);

    /**
     *
     * @param depositRequest
     * @return updated BankAccount
     */
    ServiceResponse<BankAccount> deposit(ServiceRequest<DepositRequest> depositRequest);

    /**
     *
     * @param withdrawRequest
     * @return updated BankAccount
     */
    ServiceResponse<BankAccount> withdraw(ServiceRequest<DepositRequest> withdrawRequest);

    /**
     *
     * @param moneyTransferRequest
     * @return Boolean
     */
    ServiceResponse<Boolean> transferMoney(ServiceRequest<MoneyTransferRequest> moneyTransferRequest);

    /**
     *
     * @param applicantId
     * @return List of all BankAccount of applicant
     */
    ServiceResponse<List<BankAccount>> loadAllAccounts(ServiceRequest<String> applicantId);

    /**
     *
     * @param accountId
     * @return void
     */
    ServiceResponse<Void> removeAccount(ServiceRequest<String> accountId);

    /**
     *
     * @param exchangeRequest
     * @return Price object after money exchanged
     */
    ServiceResponse<Price> exchangeCurrency(ServiceRequest<CurrencyExchangeRequest> exchangeRequest);

}
