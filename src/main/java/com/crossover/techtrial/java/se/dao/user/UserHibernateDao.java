package com.crossover.techtrial.java.se.dao.user;

import com.crossover.techtrial.java.se.common.dto.UserSearchCriteria;
import com.crossover.techtrial.java.se.common.execption.ErrorCode;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.dao.AbstractDao;
import com.crossover.techtrial.java.se.model.user.User;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This class provide hibernate dao implementation for UserDao
 */
@Repository
public class UserHibernateDao extends AbstractDao<Long, User> implements UserDao {

    public void saveUser(User user) {
        getSession().saveOrUpdate(user);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> allUsers() {
        Query query = getSession().createQuery("from User");
        return query.list();
    }

    @Override
    public User loadUserByEmail(String email) {

        Query query = getSession().createQuery("from User U where U.email=:email");
        query.setParameter("email", email);
        return (User) query.uniqueResult();
    }

    @Override
    public User loadUserById(String applicantId) {

        Query query = getSession().createQuery("from User U where U.userId=:userId");
        query.setParameter("userId", Long.parseLong(applicantId));
        return (User) query.uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> searchUserByCriteria(UserSearchCriteria searchCriteria) {

        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append("from User ");

        buildSearchQuery(searchCriteria, queryBuilder);
        Query query = getSession().createQuery(queryBuilder.toString());
        setParameters(searchCriteria, query);
        return query.list();
    }

    @Override
    public void remove(String userId) {

        User user = loadUserById(userId);

        if (user == null) {
            throw new ServiceRuntimeException(ErrorCode.USER_NOT_FOUND, "User not found");
        }
        getSession().delete(user);
    }

    private void buildSearchQuery(UserSearchCriteria searchCriteria, StringBuilder queryBuilder) {

        if (searchCriteria != null) {

            if (searchCriteria.getEmail() != null || searchCriteria.getName() != null || searchCriteria.getUserId() != null) {

                queryBuilder.append("where ");
                int count = 0;

                if (searchCriteria.getUserId() != null) {
                    queryBuilder.append("userId=:userId");
                    count++;
                }

                if (searchCriteria.getEmail() != null) {

                    if (count > 0) {
                        queryBuilder.append(" and ");
                    }
                    queryBuilder.append("email=:email");
                    count++;
                }

                if (searchCriteria.getName() != null) {

                    if (count > 0) {
                        queryBuilder.append(" and ");
                    }
                    queryBuilder.append("name=:name");
                }
            }
        }
    }

    private void setParameters(UserSearchCriteria searchCriteria, Query query) {

        if (searchCriteria != null) {

            if (searchCriteria.getEmail() != null || searchCriteria.getName() != null || searchCriteria.getUserId() != null) {

                if (searchCriteria.getUserId() != null) {
                    query.setParameter("userId", searchCriteria.getUserId());
                }

                if (searchCriteria.getEmail() != null) {
                    query.setParameter("email", searchCriteria.getEmail());
                }

                if (searchCriteria.getName() != null) {
                    query.setParameter("name", searchCriteria.getName());
                }
            }
        }
    }
}
