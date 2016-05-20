package org.write_day.web.api.v1.user;

import org.springframework.web.multipart.MultipartFile;
import org.write_day.components.enums.CommandStatus;
import org.write_day.domain.dto.ResponseResult;
import org.write_day.components.enums.user.Roles;
import org.write_day.domain.entities.user.User;
import org.write_day.domain.entities.user.UserData;
import org.write_day.services.image.ProfileImageService;
import org.write_day.services.user.UserDetailsServiceImpl;
import org.write_day.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1><b>POST заросы для обновления, изменения профиля пользователя</b></h1>
 * Created by LucidMinds on 15.05.16.
 * package org.write_day.web.api.v1.user; <br>
 */
@RestController
@RequestMapping(value = "api/v1/update")
@PropertySource(value = { "classpath:app.properties" })
@Secured({Roles.ROLE_ADMIN, Roles.ROLE_USER, Roles.ROLE_EDITOR, Roles.ROLE_SUPER_ADMIN})
public class UserUpdateController {

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ProfileImageService profileImageService;

    @Autowired
    Environment env;

    /**
     * <b>Обновить пароль</b> - host/api/v1/update/password <br>
     * <b>POST</b> запрос
     * @param password новый пароль
     * @return result : SUCCESS - успешно <br>
     *         result : ERROR - ошибка <br>
     *         result : ACCESS_DENIED - нет доступа
     * */
    @RequestMapping(value = "password", method = RequestMethod.POST)
    public ResponseResult updatePassword(@RequestParam(value = "password") String password) {
        User currentUser = userDetailsService.getCurrentUser();
        currentUser.setPassword(passwordEncoder.encode(password));
        return new ResponseResult(userService.update(currentUser));
    }

    /**
     * <b>Обновить логин</b> - host/api/v1/update/username <br>
     * <b>POST</b> запрос
     * @param username новый логин
     * @return result : SUCCESS - успешно <br>
     *         result : ERROR - ошибка <br>
     *         result : ACCESS_DENIED - нет доступа
     * */
    @RequestMapping(value = "username", method = RequestMethod.POST)
    public ResponseResult updateUsername(@RequestParam(value = "username") String username) {
        User currentUser = userDetailsService.getCurrentUser();
        currentUser.setUsername(username);
        return new ResponseResult(userService.update(currentUser));
    }

    /**
     * <b>Обновить ник</b> - host/api/v1/update/nickname <br>
     * <b>POST</b> запрос
     * @param nickname новый ник
     * @return result : SUCCESS - успешно <br>
     *         result : ERROR - ошибка <br>
     *         result : ACCESS_DENIED - нет доступа
     * */
    @RequestMapping(value = "nickname", method = RequestMethod.POST)
    public ResponseResult updateNickname(@RequestParam(value = "nickname") String nickname) {
        User currentUser = userDetailsService.getCurrentUser();
        currentUser.setNickname(nickname);
        return new ResponseResult(userService.update(currentUser));
    }

    /**
     * <b>Обновить имя</b> - host/api/v1/update/firstName <br>
     * <b>POST</b> запрос
     * @param firstName новое имя
     * @return result : SUCCESS - успешно <br>
     *         result : ERROR - ошибка <br>
     *         result : ACCESS_DENIED - нет доступа
     * */
    @RequestMapping(value = "firstName", method = RequestMethod.POST)
    public ResponseResult updateFirstName(@RequestParam(value = "firstName") String firstName) {
        User currentUser = userDetailsService.getCurrentUser();
        UserData userData = currentUser.getUserData();
        userData.setFirstName(firstName);
        return new ResponseResult(userService.update(userData));
    }

    /**
     * <b>Обновить фамилию</b> - host/api/v1/update/lastName <br>
     * <b>POST</b> запрос
     * @param lastName новая фамилия
     * @return result : SUCCESS - успешно <br>
     *         result : ERROR - ошибка <br>
     *         result : ACCESS_DENIED - нет доступа
     * */
    @RequestMapping(value = "lastName", method = RequestMethod.POST)
    public ResponseResult updateLastName(@RequestParam(value = "lastName") String lastName) {
        User currentUser = userDetailsService.getCurrentUser();
        UserData userData = currentUser.getUserData();
        userData.setLastName(lastName);
        return new ResponseResult(userService.update(userData));
    }

    /**
     * <b>Обновить номер</b> - host/api/v1/update/phone <br>
     * <b>POST</b> запрос
     * @param phone новый номер
     * @return result : SUCCESS - успешно <br>
     *         result : ERROR - ошибка <br>
     *         result : ACCESS_DENIED - нет доступа
     * */
    @RequestMapping(value = "phone", method = RequestMethod.POST)
    public ResponseResult updatePhone(@RequestParam(value = "phone") String phone) {
        User currentUser = userDetailsService.getCurrentUser();
        UserData userData = currentUser.getUserData();
        userData.setPhone(phone);
        return new ResponseResult(userService.update(userData));
    }

    /**
     * <b>Обновить email</b> - host/api/v1/update/email <br>
     * <b>POST</b> запрос
     * @param email новый email
     * @return result : SUCCESS - успешно <br>
     *         result : ERROR - ошибка <br>
     *         result : ACCESS_DENIED - нет доступа
     * */
    @RequestMapping(value = "email", method = RequestMethod.POST)
    public ResponseResult updateEmail(@RequestParam(value = "email") String email) {
        User currentUser = userDetailsService.getCurrentUser();
        UserData userData = currentUser.getUserData();
        userData.setEmail(email);
        return new ResponseResult(userService.update(userData));
    }

    /**
     * <b>Обновить изображение профиля</b> - host/api/v1/update/image <br>
     * <b>POST</b> запрос
     * @param image новое изображение профиля
     * @return result : SUCCESS - успешно <br>
     *         result : ERROR - ошибка <br>
     *         result : ACCESS_DENIED - нет доступа
     * */
    @RequestMapping(value = "image", method = RequestMethod.POST)
    public ResponseResult updateImage(@RequestParam(value = "image") MultipartFile image) {
        User currentUser = userDetailsService.getCurrentUser();
        return new ResponseResult(profileImageService.setImage(image,
                currentUser, env.getProperty("profile.image.directory")));
    }


    /**
     * <b>Обновить ник</b> - host/api/v1/update/role <br>
     * только для ROLE_ADMIN, ROLE_EDITOR, ROLE_SUPER_ADMIN <br>
     * <b>POST</b> запрос
     * @param role новый роль ROLE_USER или ROLE_EDITOR
     * @return result : SUCCESS - успешно <br>
     *         result : ERROR - ошибка <br>
     *         result : ACCESS_DENIED - нет доступа <br>
     *         result : BAD_REQUEST - неправильный запрос <br>
     * */
    @Secured({Roles.ROLE_ADMIN, Roles.ROLE_EDITOR, Roles.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "role", method = RequestMethod.POST)
    public ResponseResult updateRole(@RequestParam(value = "role") String role) {
        if (!role.equals(Roles.ROLE_USER)) {
            if (!role.equals(Roles.ROLE_EDITOR)) {
                return new ResponseResult(CommandStatus.BAD_REQUEST);
            }
        }
        User currentUser = userDetailsService.getCurrentUser();
        currentUser.setRole(role);
        return new ResponseResult(userService.update(currentUser));
    }
}
