package org.write_day.dao.message;

import org.write_day.dao.BaseDAO;
import org.write_day.domain.entities.message.dialogs.Dialog;
import org.write_day.domain.entities.message.user.UserMessage;
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
public class UserMessageDAO extends BaseDAO {

    @SuppressWarnings("unchecked")
    public List<UserMessage> getDialogMessages(Dialog dialog, String status, int max, int first) {
        Criteria criteria = getSession().createCriteria(UserMessage.class);

        criteria.add(Restrictions.eq(UserMessage.DIALOG, dialog));

        Criterion getUserStatus = Restrictions.and(Restrictions.eq(UserMessage.RECEIVER_STATUS, status));
        Criterion sendUserStatus = Restrictions.and(Restrictions.eq(UserMessage.SENDER_STATUS, status));

        LogicalExpression getUserExpression = Restrictions.or(sendUserStatus, getUserStatus);

        criteria.add(getUserExpression);
        criteria.addOrder(Order.desc(UserMessage.DATE));
        criteria.setFirstResult(first);
        criteria.setMaxResults(max);
        criteria.setCacheable(true);

        return criteria.list();
    }

}
