package com.crossover.techtrial.java.se.dao.email;

import com.crossover.techtrial.java.se.dao.AbstractDao;
import com.crossover.techtrial.java.se.model.email.EmailModel;
import org.springframework.stereotype.Repository;

@Repository("emailHibernateDao")
public class EmailHibernateDao extends AbstractDao<Long, EmailModel> implements EmailDao {

    @Override
    public void saveEmailData(EmailModel emailModel) {
        getSession().save(emailModel);
    }
}
