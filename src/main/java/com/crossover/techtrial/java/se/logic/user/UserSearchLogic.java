package com.crossover.techtrial.java.se.logic.user;

import com.crossover.techtrial.java.se.common.dto.UserSearchCriteria;
import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.dao.user.UserDao;
import com.crossover.techtrial.java.se.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class UserSearchLogic extends StatelessServiceLogic<List<User>, UserSearchCriteria> {

    @Autowired
    private UserDao userHibernateDao;

    @Transactional
    @Override
    public List<User> invoke(UserSearchCriteria searchCriteria) {
        return userHibernateDao.searchUserByCriteria(searchCriteria);
    }
}
