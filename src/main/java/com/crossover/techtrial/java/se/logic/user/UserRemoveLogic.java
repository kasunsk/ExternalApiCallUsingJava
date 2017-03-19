package com.crossover.techtrial.java.se.logic.user;

import com.crossover.techtrial.java.se.common.dto.*;
import com.crossover.techtrial.java.se.common.dto.Void;
import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.dao.user.UserDao;
import com.crossover.techtrial.java.se.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class UserRemoveLogic extends StatelessServiceLogic<Void, String> {

    @Autowired
    private UserDao userHibernateDao;

    @Transactional
    @Override
    public Void invoke(String userId) {
        ValidationUtil.validate(userId, "Invalid user id");
        userHibernateDao.remove(userId);
        return new Void();
    }
}
