package org.write_day.web.api.v1;

import org.write_day.domain.dto.ResponseResult;
import org.write_day.components.enums.CommandStatus;
import org.write_day.components.enums.user.Roles;
import org.write_day.domain.entities.user.User;
import org.write_day.domain.entities.user.UserData;
import org.write_day.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Secured({Roles.ROLE_ANONYMOUS})
public class RestRegister {

    @Autowired
    UserService userService;

    /**
     * Регистрация host/api/v1/register - POST запрос
     * @param username логин
     * @param password пароль
     * @param nickname ник
     * @param firstName имя
     * @param lastName фамилия
     * @param email email
     * @return result = SUCCESS - регистрация прошла успешно
     *         result = ERROR - ошибка при регистрации
     *         result = USERNAME_EXIST - имя пользователя занято
     * */
    @RequestMapping(value = "api/v1/register", method = RequestMethod.POST)
    @Secured({Roles.ROLE_ANONYMOUS})
    public ResponseResult register(@RequestParam(value = "username") String username,
                                   @RequestParam(value = "password") String password,
                                   @RequestParam(value = "nickname") String nickname,
                                   @RequestParam(value = "firstName") String firstName,
                                   @RequestParam(value = "lastName") String lastName,
                                   @RequestParam(value = "email") String email) {
        User user = new User();
        UserData userData = new UserData();
        user.setUsername(username);
        user.setNickname(nickname);
        user.setPassword(password);
        userData.setFirstName(firstName);
        userData.setLastName(lastName);
        userData.setEmail(email);
        user.setRole(Roles.ROLE_USER);
        CommandStatus commandStatus = userService.register(user, userData);
        return new ResponseResult(commandStatus);
    }
}
