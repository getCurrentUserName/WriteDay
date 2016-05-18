package org.write_day.services.friend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.write_day.components.enums.CommandStatus;
import org.write_day.components.enums.FriendStatus;
import org.write_day.domain.entities.friend.Friend;
import org.write_day.domain.entities.user.User;
import org.write_day.services.BaseService;

@Service
public class FriendAdd extends BaseService {

    @Autowired
    FriendUtils friendUtils;

    public CommandStatus addFriend(User currentUser, User anotherUser) {
        if (currentUser.getId().equals(anotherUser.getId())) {
            return CommandStatus.CURRENT_USER;
        }
        if (currentUser.getId() == null || anotherUser.getId() == null) {
            return CommandStatus.NULL;
        }
        Friend friend = friendUtils.getByUsers(currentUser, anotherUser);
        if (friend != null) {
            FriendStatus friendStatus = friendUtils.friendStatus(friend, currentUser);
            switch (friendStatus) {
                case SUBSCRIBER:
                    return acceptFriend(friend);
                case NEW:
                    return acceptFriend(friend);
                case IGNORE:
                    return CommandStatus.IGNORE;
                case IGNORED:
                    return CommandStatus.IGNORED;
                case FRIEND:
                    return CommandStatus.FRIEND;
                case REQUEST:
                    return CommandStatus.REQUEST;
                case FOLLOW:
                    return CommandStatus.FOLLOW;
                case NULL:
                    return newFriend(currentUser, anotherUser);
            }
        } else {
            return newFriend(currentUser, anotherUser);
        }
        return CommandStatus.ERROR;
    }

    public CommandStatus newFriend(User currentUser, User anotherUser) {
        Friend friend1 = new Friend();
        friend1.setFirstUser(currentUser);
        friend1.setSecondUser(anotherUser);
        friend1.setSecondUserStatus(FriendStatus.NEW.toString());
        friend1.setFirstUserStatus(FriendStatus.REQUEST.toString());
        return persist(friend1);
    }

    public CommandStatus acceptFriend(Friend friend) {
        friend.setSecondUserStatus(FriendStatus.FRIEND.toString());
        friend.setFirstUserStatus(FriendStatus.FRIEND.toString());
        return update(friend);
    }
}
