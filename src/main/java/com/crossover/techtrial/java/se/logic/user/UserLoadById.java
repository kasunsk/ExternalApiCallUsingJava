package com.crossover.techtrial.java.se.logic.user;

import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.dao.user.UserDao;
import com.crossover.techtrial.java.se.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class UserLoadById extends StatelessServiceLogic<User, String> {

    @Autowired
    private UserDao userHibernateDao;

    @Transactional
    @Override
    public User invoke(String applicantId) {
        return userHibernateDao.loadUserById(applicantId);
    }
}
