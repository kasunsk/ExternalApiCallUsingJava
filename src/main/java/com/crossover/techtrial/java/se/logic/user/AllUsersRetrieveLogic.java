package com.crossover.techtrial.java.se.logic.user;

import com.crossover.techtrial.java.se.common.dto.Void;
import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.dao.user.UserDao;
import com.crossover.techtrial.java.se.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class AllUsersRetrieveLogic extends StatelessServiceLogic<List<User>, Void> {

    @Autowired
    private UserDao userHibernateDao;

    @Transactional
    @Override
    public List<User> invoke(Void var) {
        List<User> users = userHibernateDao.allUsers();
        return hideUnwanted(users);
    }

    private List<User> hideUnwanted(List<User> users) {

        for (User user : users) {
            user.setUserBankAccounts(null);
        }
        return users;
    }
}
