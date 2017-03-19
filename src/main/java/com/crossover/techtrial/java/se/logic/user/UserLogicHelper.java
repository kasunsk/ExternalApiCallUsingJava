package com.crossover.techtrial.java.se.logic.user;

import com.crossover.techtrial.java.se.common.execption.ErrorCode;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.dao.user.UserDao;
import com.crossover.techtrial.java.se.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This helper class is provide all the common methods for User Logic classes.
 */
@Component
public class UserLogicHelper {

    @Autowired
    private UserDao userHibernateDao;

    protected User loadUser(String email) {

        User user = userHibernateDao.loadUserByEmail(email);

        if (user == null) {
            throw new ServiceRuntimeException(ErrorCode.USER_NOT_FOUND, "User not found");
        }
        return user;
    }


}
