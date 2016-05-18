package org.write_day.dao.message;

import org.write_day.dao.BaseDAO;
import org.write_day.domain.entities.message.dialogs.Community;
import org.write_day.domain.entities.message.Message;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class CommunityDAO extends BaseDAO {

    /** Получить сообщений группы */
    @SuppressWarnings("unchecked")
    public List<Message> getMessagesByCommunity(Community community, int max, int first) {
        Criteria criteria = getSession().createCriteria(Message.class);
        criteria.add(Restrictions.eq(Message.COMMUNITY, community));
        criteria.addOrder(Order.desc(Message.DATE));
        criteria.setFirstResult(first);
        criteria.setMaxResults(max);
        criteria.setCacheable(true);
        return criteria.list();
    }
}
