package com.crossover.techtrial.java.se.logic.account;

import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.dto.account.DepositRequest;
import com.crossover.techtrial.java.se.model.account.BankAccount;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class MoneyWithdrawLogic extends StatelessServiceLogic<BankAccount, DepositRequest> {

    @Transactional
    @Override
    public BankAccount invoke(DepositRequest var) {
        return null;
    }
}
