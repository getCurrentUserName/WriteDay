package org.write_day.components.utils;

import org.write_day.components.enums.community.UserCommunityRole;
import org.write_day.dao.message.UserCommunityDAO;
import org.write_day.domain.entities.message.dialogs.Community;
import org.write_day.domain.entities.message.dialogs.UserCommunity;
import org.write_day.domain.entities.user.User;
import org.write_day.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommunityUtils extends BaseService {

    @Autowired
    UserCommunityDAO userCommunityDAO;

    public boolean isCommunity(User user, Community community) {
        List<UserCommunity> userCommunities = userCommunityDAO.findByCommunityAndUser(community, user);
        for (UserCommunity userCommunity : userCommunities) {
            User communityUser = userCommunity.getUserId();
            if (user.getId().equals(communityUser.getId())) {
                if (!userCommunity.getRole().equals(UserCommunityRole.BAN.toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Community> getUserCommunities(User user) {
        List<UserCommunity> userCommunities = userCommunityDAO.findByUser(user);
        List<Community> communities = new ArrayList<>();
        for (UserCommunity userCommunity : userCommunities) {
            if (!userCommunity.getRole().equals(UserCommunityRole.BAN.toString())) {
                communities.add(userCommunity.getCommunity());
            }
        }
        return communities;
    }
}
