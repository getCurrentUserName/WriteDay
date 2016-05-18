package org.write_day.dao.user;

import org.write_day.dao.BaseDAO;
import org.write_day.domain.entities.images.user.ProfileImage;
import org.write_day.domain.entities.user.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ProfileImageDAO extends BaseDAO {

    public ProfileImage findByUser(User user) {
        Criteria criteria = getSession().createCriteria(ProfileImage.class);
        criteria.add(Restrictions.eq(ProfileImage.USER, user));
        criteria.setCacheable(true);
        return (ProfileImage) criteria.uniqueResult();
    }
}
