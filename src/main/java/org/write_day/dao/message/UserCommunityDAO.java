package org.write_day.dao.message;

import org.write_day.dao.BaseDAO;
import org.write_day.domain.entities.message.dialogs.Community;
import org.write_day.domain.entities.message.dialogs.UserCommunity;
import org.write_day.domain.entities.user.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserCommunityDAO extends BaseDAO {

    /**
     * Поиск связей user - community
     * @param community - группа
     * */
    @SuppressWarnings("unchecked")
    public List<UserCommunity> findByCommunity(Community community) {
        Criteria criteria = getSession().createCriteria(UserCommunity.class);
        criteria.add(Restrictions.eq(UserCommunity.COMMUNITY, community));
        criteria.setCacheable(true);
        return criteria.list();
    }

    /**
     * Поиск связей user - community
     * @param user - Пользователь
     * */
    @SuppressWarnings("unchecked")
    public List<UserCommunity> findByUser(User user) {
        Criteria criteria = getSession().createCriteria(UserCommunity.class);
        criteria.add(Restrictions.eq(UserCommunity.USER, user));
        criteria.setCacheable(true);
        return criteria.list();
    }

    /**
     * Поиск связей user - community
     * @param community - группа
     * @param user - Пользователь
     * */
    @SuppressWarnings("unchecked")
    public List<UserCommunity> findByCommunityAndUser(Community community, User user) {
        Criteria criteria = getSession().createCriteria(UserCommunity.class);
        criteria.add(Restrictions.eq(UserCommunity.COMMUNITY, community));
        criteria.add(Restrictions.eq(UserCommunity.USER, user));
        criteria.setCacheable(true);
        return criteria.list();
    }
}
