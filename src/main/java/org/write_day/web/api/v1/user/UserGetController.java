package org.write_day.web.api.v1.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.write_day.components.enums.user.Roles;
import org.write_day.domain.entities.MethodRunTime;
import org.write_day.domain.entities.user.User;
import org.write_day.services.BaseService;
import org.write_day.services.user.UserDetailsServiceImpl;
import org.write_day.services.user.UserService;

import java.util.List;
import java.util.UUID;

/**
 * <h1><b>GET заросы для пользователя</b></h1>
 * Created by LucidMinds on 15.05.16.
 * package org.write_day.web.api.v1.user; <br>
 */
@RestController
@RequestMapping(value = "api/v1")
@Secured({Roles.ROLE_ADMIN, Roles.ROLE_USER, Roles.ROLE_EDITOR, Roles.ROLE_SUPER_ADMIN})
public class UserGetController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserService userService;

    /**
     * <b>Получить профиль пользователя</b> - host/api/v1/profile <br>
     * <b>GET</b> запрос
     * @return User
     * */
    @RequestMapping(value = "profile", method = RequestMethod.GET)
    public User profile() {
        return userDetailsService.getUserProfile();
    }

    /**
     * <b>Поиск пользователя по содержанию слова в нике</b> - host/api/v1/user/nickname/{nickname}/{number} <br>
     * <b>GET</b> запрос
     * @param nickname ник
     * @param number с какого эл. начинать
     * @return Список пользователей
     * */
    @RequestMapping(value = "user/nickname/{nickname}/{number}", method = RequestMethod.GET)
    public List<User> findByNickname(@PathVariable(value = "nickname") String nickname,
                                     @PathVariable(value = "number") int number) {
        return userService.findByNickname(nickname, number);
    }

    /**
     * <b>Поиск пользователя по содержанию слова в нике</b> - host/api/v1/user/nickname/{nickname} <br>
     * <b>GET</b> запрос
     * @param nickname ник
     * @return Список пользователей
     * */
    @RequestMapping(value = "user/nickname/{nickname}", method = RequestMethod.GET)
    public List<User> findByNickname(@PathVariable(value = "nickname") String nickname) {
        return userService.findByNickname(nickname, 0);
    }

    /**
     * <b>Поиск пользователя по id</b> - host/api/v1/user/{id} <br>
     * <b>GET</b> запрос
     * @param id id пользователя
     * @return User
     * */
    @RequestMapping(value = "user/{id}", method = RequestMethod.GET)
    public User findByNickname(@PathVariable(value = "id") UUID id) {
        return userService.findById(id);
    }
}
