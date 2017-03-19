package com.crossover.techtrial.java.se.logic.user;

import com.crossover.techtrial.java.se.common.dto.Void;
import com.crossover.techtrial.java.se.common.execption.ErrorCode;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.dao.user.UserDao;
import com.crossover.techtrial.java.se.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class UserAuthenticateLogic extends StatelessServiceLogic<Void, String> {

    @Autowired
    private UserDao userHibernateDao;

    @Transactional
    @Override
    public Void invoke(String applicantId) {

        User user = userHibernateDao.loadUserById(applicantId);

        if (user == null) {
            throw new ServiceRuntimeException(ErrorCode.USER_NOT_FOUND, "Applicant not exist");
        }
        return new Void();
    }
}
