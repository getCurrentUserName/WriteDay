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

/**
 * <h1><b>Опреции над друзьями</b></h1>
 * Created by LucidMinds on 15.05.16.
 * */
@RestController
@RequestMapping(value = "api/v1/friend")
@Secured({Roles.ROLE_ADMIN, Roles.ROLE_USER, Roles.ROLE_EDITOR, Roles.ROLE_SUPER_ADMIN})
public class FriendController {

    @Autowired
    private FriendAdd friendAdd;
    @Autowired
    private FriendDelete friendDelete;
    @Autowired
    private FriendUtils getFriendByStatus;
    @Autowired
    private FriendUtils friendUtils;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserService userService;

    /**
     * <b>Получить список пользователей подавших заявку на дружбу</b> - <i>host/api/v1/friend/new</i> <br>
     * <b>GET</b> запрос
     * @return Списиок пользователей
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
     * <b>Получить список пользователей которым отправили заявку на дружбу </b>- <i>host/api/v1/friend/requests</i> <br>
     * <b>GET</b> запрос
     * @return Список пользователей
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
     * <b>Получить список пользователей с которыми в состоянии дружбы</b> - <i>host/api/v1/friend/friends</i> <br>
     * <b>GET</b> запрос
     * @return Список пользователей
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
     * <b>Получить список подписчиков</b> - <i>host/api/v1/friend/subscribers</i> <br>
     * <b>GET</b> запрос
     * @return Список пользователей
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
     * <b>Получить список пользователей на которых отправил запрос на дружбу</b> - <i>host/api/v1/friend/follow</i> <br>
     * <b>GET</b> запрос
     * @return Список пользователей
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
     * <b>Узнать состояние дружбы с определенным пользователем</b> - <i>host/api/v1/friend/status/{id}</i> <br>
     * <b>GET</b> запрос
     * @param id id пользователя
     * @return FRIEND - друзья <br>
     * REQUEST - отправил заявку <br>
     * NEW - получил заявку <br>
     * FOLLOW - подписан <br>
     * SUBSCRIBER - подписчик <br>
     * CURRENT_USER - текущий пользователь <br>
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
     * <b>Отправить, принять запрос на дружбу</b> - <i>host/api/v1/friend/add</i> <br>
     * <b>POST</b> запрос
     * @param id id пользователя
     * @return
     *  SUCCESS - добавлено <br>
     *  FRIEND - друг <br>
     *  REQUEST - запрос был отправлен <br>
     *  CURRENT_USER - сам себя <br>
     *  ERROR - ошибка
     *  */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseResult add(@RequestParam(value = "id") UUID id) {
        User user = userDetailsService.getCurrentUser();
        User secondUser = userService.findById(id);
        return new ResponseResult(friendAdd.addFriend(user, secondUser));
    }

    /**
     * <b>Удалить из друзей, отменить подписку</b> - <i>host/api/v1/friend/delete</i> <br>
     * <b>POST</b> запрос
     * @param id id пользователя
     * @return
     *  SUCCESS - удален и добавлен в список подписчиков <br>
     *  SUBSCRIBER - раннее был удален и находится в списке подписчиков <br>
     *  ERROR - ошибка
     * */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseResult delete(@RequestParam(value = "id") UUID id) {
        User user = userDetailsService.getCurrentUser();
        User secondUser = userService.findById(id);
        return new ResponseResult(friendDelete.deleteFriend(user, secondUser));
    }
}
