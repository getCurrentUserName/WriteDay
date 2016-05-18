package org.write_day.dao.user;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.write_day.dao.BaseDAO;
import org.write_day.domain.entities.user.UserData;

import java.util.UUID;

@Repository
@Transactional
public class UserDataDAO extends BaseDAO {

    public UserData findById(UUID id) {
        return findById(UserData.class, id);
    }

    public UserData findByEmail(String email) {
        Criteria criteria = getSession().createCriteria(UserData.class);
        criteria.add(Restrictions.eq(UserData.EMAIL, email));
        criteria.setCacheable(true);
        return (UserData) criteria.uniqueResult();
    }
}
