package com.crossover.techtrial.java.se.logic.account;

import com.crossover.techtrial.java.se.common.execption.ErrorCode;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.dao.account.AccountDao;
import com.crossover.techtrial.java.se.model.account.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
public class AllAccountsLoadingLogic extends StatelessServiceLogic<List<BankAccount>, String> {

    @Autowired
    private AccountDao accountDao;

    @Transactional
    @Override
    public List<BankAccount> invoke(String applicantId) {

        validateInput(applicantId);
        return (accountDao.loadAccountByApplicantId(applicantId) == null ? new ArrayList<>() : accountDao
                .loadAccountByApplicantId(applicantId));
    }

    private void validateInput(String applicantId) {

        if (applicantId == null || applicantId.isEmpty()) {
            throw new ServiceRuntimeException(ErrorCode.INVALID_INPUT, "Applicant id is null");
        }
    }
}
