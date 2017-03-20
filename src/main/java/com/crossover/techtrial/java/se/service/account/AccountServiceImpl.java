package com.crossover.techtrial.java.se.service.account;

import com.crossover.techtrial.java.se.common.dto.*;
import com.crossover.techtrial.java.se.common.service.RequestAssembler;
import com.crossover.techtrial.java.se.dto.account.*;
import com.crossover.techtrial.java.se.logic.account.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by kasun on 2/4/17.
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountCreateLogic accountCreateLogic;

    @Autowired
    private MoneyDepositLogic moneyDepositLogic;

    @Autowired
    private AllAccountsLoadingLogic allAccountsLoadingLogic;

    @Override
    public ServiceResponse<Account> createAccount(ServiceRequest<AccountCreateCriteria> criteria) {

        return RequestAssembler.assemble(accountCreateLogic, criteria);
    }

    @Override
    public ServiceResponse<Account> deposit(ServiceRequest<MoneyTransferRequest> depositRequest) {

        return RequestAssembler.assemble(moneyDepositLogic, depositRequest);
    }


    @Override
    public ServiceResponse<List<Account>> loadAllAccounts(ServiceRequest<String> userId) {

        return RequestAssembler.assemble(allAccountsLoadingLogic, userId);
    }

}
