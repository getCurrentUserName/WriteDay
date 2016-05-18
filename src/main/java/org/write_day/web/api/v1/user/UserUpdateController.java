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
     * Обновить пароль - host/api/v1/update/password
     * @param password новый пароль
     * @return result : SUCCESS - успешно
     *         result : ERROR - ошибка
     *         result : ACCESS_DENIED - нет доступа
     * */
    @RequestMapping(value = "password", method = RequestMethod.POST)
    public ResponseResult updatePassword(@RequestParam(value = "password") String password) {
        User currentUser = userDetailsService.getCurrentUser();
        currentUser.setPassword(passwordEncoder.encode(password));
        return new ResponseResult(userService.update(currentUser));
    }

    /**
     * Обновить логин - host/api/v1/update/username
     * @param username новый логин
     * @return result : SUCCESS - успешно
     *         result : ERROR - ошибка
     *         result : ACCESS_DENIED - нет доступа
     * */
    @RequestMapping(value = "username", method = RequestMethod.POST)
    public ResponseResult updateUsername(@RequestParam(value = "username") String username) {
        User currentUser = userDetailsService.getCurrentUser();
        currentUser.setUsername(username);
        return new ResponseResult(userService.update(currentUser));
    }

    /**
     * Обновить ник - host/api/v1/update/nickname
     * @param nickname новый ник
     * @return result : SUCCESS - успешно
     *         result : ERROR - ошибка
     *         result : ACCESS_DENIED - нет доступа
     * */
    @RequestMapping(value = "nickname", method = RequestMethod.POST)
    public ResponseResult updateNickname(@RequestParam(value = "nickname") String nickname) {
        User currentUser = userDetailsService.getCurrentUser();
        currentUser.setNickname(nickname);
        return new ResponseResult(userService.update(currentUser));
    }

    /**
     * Обновить имя - host/api/v1/update/firstName
     * @param firstName новое имя
     * @return result : SUCCESS - успешно
     *         result : ERROR - ошибка
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
     * Обновить фамилию - host/api/v1/update/lastName
     * @param lastName новая фамилия
     * @return result : SUCCESS - успешно
     *         result : ERROR - ошибка
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
     * Обновить номер - host/api/v1/update/phone
     * @param phone новый номер
     * @return result : SUCCESS - успешно
     *         result : ERROR - ошибка
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
     * Обновить email - host/api/v1/update/email
     * @param email новый email
     * @return result : SUCCESS - успешно
     *         result : ERROR - ошибка
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
     * Обновить изображение профиля - host/api/v1/update/image
     * @param image новое изображение профиля
     * @return result : SUCCESS - успешно
     *         result : ERROR - ошибка
     *         result : ACCESS_DENIED - нет доступа
     * */
    @RequestMapping(value = "image", method = RequestMethod.POST)
    public ResponseResult updateImage(@RequestParam(value = "image") MultipartFile image) {
        User currentUser = userDetailsService.getCurrentUser();
        return new ResponseResult(profileImageService.setImage(image,
                currentUser, env.getProperty("profile.image.directory")));
    }


    /**
     * Обновить ник - host/api/v1/update/role
     * @param role новый роль ROLE_USER или ROLE_EDITOR
     * @return result : SUCCESS - успешно
     *         result : ERROR - ошибка
     *         result : ACCESS_DENIED - нет доступа
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
