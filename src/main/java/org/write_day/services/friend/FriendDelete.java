package org.write_day.services.friend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.write_day.components.enums.CommandStatus;
import org.write_day.components.enums.FriendStatus;
import org.write_day.domain.entities.friend.Friend;
import org.write_day.domain.entities.user.User;
import org.write_day.services.BaseService;

@Service
public class FriendDelete extends BaseService {

    @Autowired
    FriendUtils friendUtils;

    public CommandStatus deleteFriend(User currentUser, User anotherUser) {
        if (currentUser.getId().equals(anotherUser.getId())) {
            return CommandStatus.CURRENT_USER;
        }

        Friend friend = friendUtils.getByUsers(currentUser, anotherUser);
        if (friend != null) {
            FriendStatus friendStatus = friendUtils.friendStatus(friend, currentUser);
            switch (friendStatus) {
                case SUBSCRIBER:
                    return CommandStatus.SUBSCRIBER;
                case NEW:
                    return delete(currentUser, friend);
                case IGNORE:
                    return CommandStatus.IGNORE;
                case IGNORED:
                    return CommandStatus.IGNORED;
                case FRIEND:
                    return delete(currentUser, friend);
                case REQUEST:
                    return cancelSubscribe(friend);
                case FOLLOW:
                    return cancelSubscribe(friend);
            }
        } else {
            return CommandStatus.EMPTY;
        }
        return CommandStatus.ERROR;
    }

    /** ОБНОВЛЯЕТ КАК ПОДПИСЧИК */
    public CommandStatus delete(User currentUser, Friend friend) {
        if (friend.getFirstUser().getId().equals(currentUser.getId())) {
            friend.setFirstUserStatus(FriendStatus.SUBSCRIBER.toString());
            friend.setSecondUserStatus(FriendStatus.FOLLOW.toString());
            return update(friend);
        }
        if (friend.getSecondUser().getId().equals(currentUser.getId())) {
            friend.setFirstUserStatus(FriendStatus.FOLLOW.toString());
            friend.setSecondUserStatus(FriendStatus.SUBSCRIBER.toString());
            return update(friend);
        }
        return cancelSubscribe(friend);
    }

    /** ОТМЕНИТЬ ПОДПИСКУ */
    public CommandStatus cancelSubscribe(Friend friend) {
        return delete(friend);
    }
}
