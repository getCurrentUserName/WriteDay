package org.write_day.web.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.write_day.components.enums.FriendStatus;
import org.write_day.components.enums.user.Roles;
import org.write_day.domain.dto.ResponseResult;
import org.write_day.domain.entities.MethodRunTime;
import org.write_day.domain.entities.user.User;
import org.write_day.services.friend.FriendAdd;
import org.write_day.services.friend.FriendDelete;
import org.write_day.services.friend.FriendUtils;
import org.write_day.services.user.UserDetailsServiceImpl;
import org.write_day.services.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/v1/friend")
@Secured({Roles.ROLE_ADMIN, Roles.ROLE_USER, Roles.ROLE_EDITOR, Roles.ROLE_SUPER_ADMIN})
public class FriendController {

    @Autowired
    FriendAdd friendAdd;
    @Autowired
    FriendDelete friendDelete;
    @Autowired
    FriendUtils getFriendByStatus;
    @Autowired
    FriendUtils friendUtils;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    UserService userService;

    /**
     * список пользователей подавших заявку на дружбу
     * @return Пользователей
     * */
    @RequestMapping(value = "new", method = RequestMethod.GET)
    public List<User> newUsers() {
        long start = System.currentTimeMillis();
        List<User> users = new ArrayList<>();
        try {
            return getFriendByStatus.getFriendsByStatus(FriendStatus.NEW);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long finish = System.currentTimeMillis();
        userService.asyncPersist(new MethodRunTime("org.write_day.web.api.v1.user",
                "UserGetController",
                "profile",
                (finish - start)));
        return users;
    }

    /**
     * список пользователей которым подали заявку на дружбу
     * @return Пользователей
     * */
    @RequestMapping(value = "requests", method = RequestMethod.GET)
    public List<User> requestUsers() {
        long start = System.currentTimeMillis();
        List<User> users = new ArrayList<>();
        try {
            return getFriendByStatus.getFriendsByStatus(FriendStatus.REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long finish = System.currentTimeMillis();
        userService.asyncPersist(new MethodRunTime("org.write_day.web.api.v1.user",
                "UserGetController",
                "profile",
                (finish - start)));
        return users;
    }

    /**
     * список пользователей с которыми в состоянии дружбы
     * @return Пользователей
     * */
    @RequestMapping(value = "friends", method = RequestMethod.GET)
    public List<User> getFriends() {
        long start = System.currentTimeMillis();
        List<User> users = new ArrayList<>();
        try {
            return getFriendByStatus.getFriendsByStatus(FriendStatus.FRIEND);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long finish = System.currentTimeMillis();
        userService.asyncPersist(new MethodRunTime("org.write_day.web.api.v1.user",
                "UserGetController",
                "profile",
                (finish - start)));
        return users;
    }

    /**
     * список подписанных пользователей
     * @return Пользователей
     * */
    @RequestMapping(value = "subscribers", method = RequestMethod.GET)
    public List<User> getSubscribers() {
        long start = System.currentTimeMillis();
        List<User> users = new ArrayList<>();
        try {
            return getFriendByStatus.getFriendsByStatus(FriendStatus.SUBSCRIBER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long finish = System.currentTimeMillis();
        userService.asyncPersist(new MethodRunTime("org.write_day.web.api.v1.user",
                "UserGetController",
                "profile",
                (finish - start)));
        return users;
    }

    /**
     * список пользователей на которых отправил запрос на дружбу
     * @return Пользователей
     * */
    @RequestMapping(value = "follow", method = RequestMethod.GET)
    public List<User> getRequestUsers() {
        long start = System.currentTimeMillis();
        List<User> users = new ArrayList<>();
        try {
            return getFriendByStatus.getFriendsByStatus(FriendStatus.FOLLOW);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long finish = System.currentTimeMillis();
        userService.asyncPersist(new MethodRunTime("org.write_day.web.api.v1.user",
                "UserGetController",
                "profile",
                (finish - start)));
        return users;
    }

    /**
     * узнать состояние дружбы с определенным пользователем
     * @param id id пользователя
     * @return FRIEND - друзья
     * REQUEST - отправил заявку
     * NEW - получил заявку
     * FOLLOW - подписан
     * SUBSCRIBER - подписчик
     * CURRENT_USER - текущий пользователь
     * NULL - не состоит в дружеских отношениях
     * */
    @RequestMapping(value = "status/{id}", method = RequestMethod.GET)
    public ResponseResult getIgnoreUsers(@PathVariable(value = "id") UUID id) {
        long start = System.currentTimeMillis();
        User currentUser = userDetailsService.getCurrentUser();
        User anotherUser = userService.findById(id);
        FriendStatus friendStatus = friendUtils.friendStatus(currentUser, anotherUser);
        long finish = System.currentTimeMillis();
        userService.asyncPersist(new MethodRunTime("org.write_day.web.api.v1.user",
                "UserGetController",
                "profile",
                (finish - start)));
        return new ResponseResult(friendStatus);
    }

    /**
     * Отправить запрос на дружбу
     * @param id id пользователя
     * @return
     *  SUCCESS - добавлено
     *  FRIEND - друг
     *  REQUEST - запрос был отправлен
     *  CURRENT_USER - сам себя
     *  ERROR - ошибка
     *  */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseResult add(@RequestParam(value = "id") UUID id) {
        User user = userDetailsService.getCurrentUser();
        User secondUser = userService.findById(id);
        return new ResponseResult(friendAdd.addFriend(user, secondUser));
    }

    /**
     * Удалить из друзей, отменить подписку
     * @param id id пользователя
     * @return
     *  SUCCESS - удален и добавлен в список подписчиков
     *  SUBSCRIBER - раннее был удален и находится в списке подписчиков
     *  ERROR - ошибка
     * */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseResult delete(@RequestParam(value = "id") UUID id) {
        User user = userDetailsService.getCurrentUser();
        User secondUser = userService.findById(id);
        return new ResponseResult(friendDelete.deleteFriend(user, secondUser));
    }
}
