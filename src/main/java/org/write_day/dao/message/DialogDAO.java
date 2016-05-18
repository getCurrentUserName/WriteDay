package org.write_day.dao.message;

import org.write_day.dao.BaseDAO;
import org.write_day.domain.entities.message.dialogs.Dialog;
import org.write_day.domain.entities.user.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class DialogDAO extends BaseDAO {

    @SuppressWarnings("unchecked")
    public List<Dialog> getUserDialogs(User user, String status) {
        Criteria criteria = getSession().createCriteria(Dialog.class);

        Criterion rest1 = Restrictions.and(Restrictions.eq(Dialog.FIRST_USER, user));
        Criterion rest2 = Restrictions.and(Restrictions.eq(Dialog.FIRST_USER_STATUS, status));

        Criterion rest3 = Restrictions.and(Restrictions.eq(Dialog.SECOND_USER, user));
        Criterion rest4 = Restrictions.and(Restrictions.eq(Dialog.SECOND_USER_STATUS, status));

        LogicalExpression expression = Restrictions.and(rest1, rest2);
        LogicalExpression expression2 = Restrictions.and(rest3, rest4);

        LogicalExpression expression3 = Restrictions.or(expression2, expression);
        criteria.add(expression3);
        criteria.addOrder(Order.desc(Dialog.DATE));
        criteria.setCacheable(true);
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    public Dialog getDialogByUsers(User user, User anotherUser) {
        Criteria criteria = getSession().createCriteria(Dialog.class);

        Criterion rest1 = Restrictions.and(Restrictions.eq(Dialog.SECOND_USER, anotherUser));
        Criterion rest2 = Restrictions.and(Restrictions.eq(Dialog.FIRST_USER, user));

        Criterion rest3 = Restrictions.and(Restrictions.eq(Dialog.SECOND_USER, user));
        Criterion rest4 = Restrictions.and(Restrictions.eq(Dialog.FIRST_USER, anotherUser));

        LogicalExpression expression = Restrictions.and(rest1, rest2);
        LogicalExpression expression2 = Restrictions.and(rest3, rest4);

        LogicalExpression expression3 = Restrictions.or(expression2, expression);
        criteria.add(expression3);
        criteria.setCacheable(true);
        return (Dialog) criteria.uniqueResult();
    }
}
