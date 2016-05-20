package org.write_day.services.friend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.write_day.components.enums.FriendStatus;
import org.write_day.dao.friend.FriendDao;
import org.write_day.domain.entities.friend.Friend;
import org.write_day.domain.entities.images.user.ProfileImage;
import org.write_day.domain.entities.user.User;
import org.write_day.services.image.ProfileImageService;
import org.write_day.services.user.UserDetailsServiceImpl;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendUtils {

    @Autowired
    FriendDao friendDao;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    ProfileImageService profileImageService;

    public boolean isFriend(User current_user, User another_user) {
        FriendStatus friendStatus = friendStatus(current_user, another_user);
        return friendStatus == FriendStatus.FRIEND;
    }

    public FriendStatus friendStatus(Friend friend, User currentUser) {
        if (friend == null) {
            return FriendStatus.NULL;
        }
        User anotherUser = friend.getSecondUser();
        User firstUser = friend.getFirstUser();

        if (currentUser.getId().equals(anotherUser.getId())) {
            return FriendStatus.valueOf(friend.getSecondUserStatus());
        }
        if (currentUser.getId().equals(firstUser.getId())) {
            return FriendStatus.valueOf(friend.getFirstUserStatus());
        }
        return FriendStatus.NULL;
    }

    public FriendStatus friendStatus(User currentUser, User anotherUser) {
        Friend friend = getByUsers(currentUser, anotherUser);
        return friendStatus(friend, currentUser);
    }

    public Friend getByUsers(User currentUser, User anotherUser) {
        return friendDao.getByUsers(currentUser, anotherUser);
    }

    public List<Friend> getByUserAndStatus(User currentUser, String status) {
        return friendDao.getByUserAndStatus(currentUser, status);
    }

    public List<User> getFriendsByStatus(FriendStatus friendStatus) {
        User user = userDetailsService.getCurrentUser();
        return getFriendsByStatus(user, friendStatus);
    }

    /** ПОЛУЧИТЬ ПОЛЬЗОВАТЕЛЕЙ ПО СТАТУСУ ОТНОШЕНИЯ PS: НАПРИМЕР ДРУЗЕЙ, ВЕРНЕТ ТОЛЬКО ДРУЗЕЙ */
    public List<User> getFriendsByStatus(User user, FriendStatus friendStatus) {
        List<User> userList = new ArrayList<>();
        List<Friend> friendList = friendDao.getByUserAndStatus(user, friendStatus.toString());
        for (Friend friend : friendList) {
            User firstUser = friend.getFirstUser();
            User secondUser = friend.getSecondUser();
            if (firstUser.getId().equals(user.getId())) {
                ProfileImage profileImage = profileImageService.findByUser(secondUser);
                secondUser.setProfileImage(profileImage);
                userList.add(secondUser);
            } else if (secondUser.getId().equals(user.getId())) {
                ProfileImage profileImage = profileImageService.findByUser(firstUser);
                firstUser.setProfileImage(profileImage);
                userList.add(firstUser);
            }
        }
        return userList;
    }
}
