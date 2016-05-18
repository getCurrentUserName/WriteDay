package org.write_day.dao.friend;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.write_day.dao.BaseDAO;
import org.write_day.domain.entities.friend.Friend;
import org.write_day.domain.entities.user.User;

import java.util.List;

@Repository
@Transactional
public class FriendDao extends BaseDAO {

    @SuppressWarnings("unchecked")
    public List<Friend> getByUser(User user) {
        Criteria criteria = getSession().createCriteria(Friend.class);

        Criterion rest2 = Restrictions.and(Restrictions.eq(Friend.FIRST_USER, user));
        Criterion rest1 = Restrictions.and(Restrictions.eq(Friend.SECOND_USER, user));

        criteria.add(Restrictions.or(rest1, rest2));
        criteria.setCacheable(true);
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    public Friend getByUsers(User user, User anotherUser) {
        Criteria criteria = getSession().createCriteria(Friend.class);

        Criterion rest1 = Restrictions.and(Restrictions.eq(Friend.SECOND_USER, anotherUser));
        Criterion rest2 = Restrictions.and(Restrictions.eq(Friend.FIRST_USER, user));

        Criterion rest3 = Restrictions.and(Restrictions.eq(Friend.SECOND_USER, user));
        Criterion rest4 = Restrictions.and(Restrictions.eq(Friend.FIRST_USER, anotherUser));

        LogicalExpression expression = Restrictions.and(rest1, rest2);
        LogicalExpression expression2 = Restrictions.and(rest3, rest4);
        LogicalExpression expression3 = Restrictions.or(expression2, expression);
        criteria.add(expression3);
        criteria.setCacheable(true);
        return (Friend) criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<Friend> getByUserAndStatus(User user, String status) {
        Criteria criteria = getSession().createCriteria(Friend.class);

        Criterion rest1 = Restrictions.and(Restrictions.eq(Friend.FIRST_USER, user));
        Criterion rest2 = Restrictions.and(Restrictions.eq(Friend.FIRST_USER_STATUS, status));

        Criterion rest3 = Restrictions.and(Restrictions.eq(Friend.SECOND_USER, user));
        Criterion rest4 = Restrictions.and(Restrictions.eq(Friend.SECOND_USER_STATUS, status));

        LogicalExpression expression = Restrictions.and(rest1, rest2);
        LogicalExpression expression2 = Restrictions.and(rest3, rest4);

        LogicalExpression expression3 = Restrictions.or(expression2, expression);
        criteria.add(expression3);
        criteria.setCacheable(true);

        return criteria.list();
    }
}
