package com.crossover.techtrial.java.se.service.account;

import com.crossover.techtrial.java.se.common.dto.*;
import com.crossover.techtrial.java.se.common.dto.Void;
import com.crossover.techtrial.java.se.common.service.RequestAssembler;
import com.crossover.techtrial.java.se.dao.account.AccountDao;
import com.crossover.techtrial.java.se.dto.account.AccountRequest;
import com.crossover.techtrial.java.se.dto.account.DepositRequest;
import com.crossover.techtrial.java.se.dto.account.MoneyTransferRequest;
import com.crossover.techtrial.java.se.logic.account.*;
import com.crossover.techtrial.java.se.model.account.BankAccount;
import com.crossover.techtrial.java.se.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by kasun on 2/4/17.
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountDao accountHibernateDao;

    @Autowired
    UserService userService;

    @Autowired
    private AccountCreateLogic accountCreateLogic;

    @Autowired
    private MoneyDepositLogic moneyDepositLogic;

    @Autowired
    private MoneyWithdrawLogic moneyWithdrawLogic;

    @Autowired
    private MoneyTransferLogic moneyTransferLogic;

    @Autowired
    private AllAccountsLoadingLogic allAccountsLoadingLogic;

    @Autowired
    private AccountRemoveLogic accountRemoveLogic;

    @Autowired
    private CurrencyExchangeLogic currencyExchangeLogic;

    @Override
    public ServiceResponse<Account> createAccount(ServiceRequest<AccountCreateCriteria> criteria) {

        return RequestAssembler.assemble(accountCreateLogic, criteria);
    }

    @Override
    public ServiceResponse<Account> deposit(ServiceRequest<MoneyTransferRequest> depositRequest) {

        return RequestAssembler.assemble(moneyDepositLogic, depositRequest);
    }

    @Override
    public ServiceResponse<BankAccount> withdraw(ServiceRequest<DepositRequest> withdrawRequest) {

        return RequestAssembler.assemble(moneyWithdrawLogic, withdrawRequest);
    }

    @Override
    public ServiceResponse<Boolean> transferMoney(ServiceRequest<MoneyTransferRequest> moneyTransferRequest) {

        return RequestAssembler.assemble(moneyTransferLogic, moneyTransferRequest);
    }

    @Override
    public ServiceResponse<List<Account>> loadAllAccounts(ServiceRequest<String> userId) {

        return RequestAssembler.assemble(allAccountsLoadingLogic, userId);
    }

    @Override
    public ServiceResponse<Void> removeAccount(ServiceRequest<String> accountId) {

        return RequestAssembler.assemble(accountRemoveLogic,accountId);
    }

    @Override
    public ServiceResponse<Price> exchangeCurrency(ServiceRequest<CurrencyExchangeRequest> exchangeRequest) {

        return RequestAssembler.assemble(currencyExchangeLogic,exchangeRequest);
    }
}
