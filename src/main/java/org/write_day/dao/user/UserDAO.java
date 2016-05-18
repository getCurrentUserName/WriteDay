package org.write_day.dao.user;

import org.hibernate.FetchMode;
import org.write_day.dao.BaseDAO;
import org.write_day.domain.entities.user.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public class UserDAO extends BaseDAO {

    public User findById(UUID id) {
        return findById(User.class, id);
    }

    public User findByUsername(String username) {
        Criteria criteria = getSession().createCriteria(User.class);
        criteria.add(Restrictions.eq(User.USERNAME, username));
        criteria.setCacheable(true);
        return (User) criteria.uniqueResult();
    }

    public User getUserProfile(String username) {
        Criteria criteria = getSession().createCriteria(User.class);
        criteria.add(Restrictions.eq(User.USERNAME, username));
        criteria.setFetchMode(User.USER_DATA, FetchMode.JOIN);
        criteria.setFetchMode("profileImage", FetchMode.JOIN);
        criteria.setCacheable(true);
        return (User) criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<User> findByNickname(String nickname, int max, int first) {
        Criteria criteria = getSession().createCriteria(User.class);
        criteria.add(Restrictions.eq(User.NICKNAME, nickname));
        criteria.setFetchMode(User.USER_DATA, FetchMode.JOIN);
        criteria.setFetchMode("profileImage", FetchMode.JOIN);
        criteria.setFirstResult(first);
        criteria.setMaxResults(max);
        criteria.setCacheable(true);
        return criteria.list();
    }
}
