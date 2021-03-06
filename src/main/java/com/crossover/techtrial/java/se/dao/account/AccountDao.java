package com.crossover.techtrial.java.se.dao.account;

import com.crossover.techtrial.java.se.model.account.BankAccount;
import com.crossover.techtrial.java.se.model.user.UserTicket;

import java.util.List;

/**
 * This interface provide the database access methods
 */
public interface AccountDao {

    List<BankAccount> loadAccountByApplicantId(String applicantId);

    BankAccount loadAccountById(Long accountId);

    BankAccount createAccount(BankAccount bankAccount);
    
    void saveUserTicket(UserTicket userTicket);

}
