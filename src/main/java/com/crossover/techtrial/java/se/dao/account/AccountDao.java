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

    void updateAccount(BankAccount applicantBankAccount);

    BankAccount createAccount(BankAccount bankAccount);
    
    void deleteAccount(BankAccount bankAccount);

    void saveUserTicket(UserTicket userTicket);

    void removeAccount(String accountId);
}
