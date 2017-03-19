package com.crossover.techtrial.java.se.logic.account;

import com.crossover.techtrial.java.se.common.dto.*;
import com.crossover.techtrial.java.se.common.dto.Void;
import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.dao.account.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import static com.crossover.techtrial.java.se.util.ValidationUtil.validate;

@Component
public class AccountRemoveLogic extends StatelessServiceLogic<Void, String> {

    @Autowired
    private AccountDao accountHibernateDao;

    @Transactional
    @Override
    public Void invoke(String accountId) {

        validate(accountId, "Invalid account Id");
        accountHibernateDao.removeAccount(accountId);
        return new Void();
    }
}
