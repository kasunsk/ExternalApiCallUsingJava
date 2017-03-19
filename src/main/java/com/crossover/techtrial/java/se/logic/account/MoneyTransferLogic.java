package com.crossover.techtrial.java.se.logic.account;

import com.crossover.techtrial.java.se.common.execption.ErrorCode;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.dao.account.AccountDao;
import com.crossover.techtrial.java.se.dto.account.MoneyTransferRequest;
import com.crossover.techtrial.java.se.model.account.BankAccount;
import com.crossover.techtrial.java.se.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class MoneyTransferLogic extends StatelessServiceLogic<Boolean, MoneyTransferRequest> {

    @Autowired
    private AccountDao accountHibernateDao;

    @Transactional
    @Override
    public Boolean invoke(MoneyTransferRequest request) {

        validateRequest(request);
//        BankAccount bankAccount = accountHibernateDao.loadAccountById(Long.parseLong(request.getAccountNumber()));
//
//        if (bankAccount == null) {
//            throw new ServiceRuntimeException(ErrorCode.ACCOUNT_NOT_EXIST, "Account not exist");
//        }
//        Double newAmount;
//
//        //TODO currency exchange should apply here
//        if (request.getTransferType().equals(MoneyTransferRequest.TransferType.DEPOSIT)) {
//         //   newAmount = bankAccount.getAvailableAmount() + request.getTransferAmount();
//
//        } else {
//           // newAmount = bankAccount.getAvailableAmount() - request.getTransferAmount();
//        }
//
//        if (newAmount >= 0) {
//            bankAccount.setAvailableAmount(newAmount);
//        } else {
//            return false;
//        }
//
//        accountHibernateDao.updateAccount(bankAccount);
        return true;
    }

    private void validateRequest(MoneyTransferRequest request) {

//        ValidationUtil.validate(request, "Invalid money transfer request");
//        ValidationUtil.validate(request.getAccountId(), "Invalid account number");
//        ValidationUtil.validate(request.getTransferAmount(), "Transfer amount is null");
//        ValidationUtil.validate(request.getCurrency(), "Currency is null");
//        ValidationUtil.validate(request.getTransferType(), "Transfer type is empty");
    }
}
