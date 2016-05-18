package org.write_day.services.message;

import org.write_day.components.enums.CommandStatus;
import org.write_day.components.enums.community.UserCommunityRole;
import org.write_day.domain.entities.message.dialogs.Community;
import org.write_day.domain.entities.message.dialogs.UserCommunity;
import org.write_day.domain.entities.user.User;
import org.write_day.services.BaseService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class CommunityService extends BaseService {

    public CommandStatus createCommunity(User user) {
        Community community = new Community();
        community.setDate(new Date());
        UUID uuid = save(community);
        community.setId(uuid);
        return communityAddUser(community, user, UserCommunityRole.FOUNDER);
    }

    public CommandStatus communityAddUser(Community community, User user, UserCommunityRole userCommunityRole) {
        UserCommunity userCommunity = new UserCommunity();
        userCommunity.setUserId(user);
        userCommunity.setCommunity(community);
        userCommunity.setRole(userCommunityRole.toString());
        userCommunity.setDate(new Date());
        return persist(userCommunity);
    }
}
