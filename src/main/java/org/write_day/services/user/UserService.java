package org.write_day.services.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.write_day.dao.user.UserDAO;
import org.write_day.components.enums.CommandStatus;
import org.write_day.dao.user.UserDataDAO;
import org.write_day.domain.entities.user.User;
import org.write_day.domain.entities.user.UserData;
import org.write_day.domain.entities.user.UserSettings;
import org.write_day.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService extends BaseService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserDataDAO userDataDAO;
    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * Поиск по id
     * @param id - id пользователя
     * */
    public User findById(UUID id) {
        return userDAO.findById(id);
    }

    /**
     * Поиск по логину
     * @param username - логин пользователя
     * */
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    public User getUserProfile(String username) {
        return userDAO.getUserProfile(username);
    }

    public List<User> findByNickname(String nickname, int max, int first) {
        return userDAO.findByNickname(nickname, max, first);
    }

    public CommandStatus register(User registerUser, UserData registerUserData) {
        UserSettings userSettings = new UserSettings();
        userSettings.setMessageAccess("ALL");
        User user = findByUsername(registerUser.getUsername());
        UserData userData = userDataDAO.findByEmail(registerUserData.getEmail());
        if (userData != null) {
            return CommandStatus.EMAIL_EXIST;
        }
        if (user != null) {
            return CommandStatus.USERNAME_EXIST;
        }
        String password = passwordEncoder.encode(registerUser.getPassword());
        registerUser.setPassword(password);
        registerUser.setUserData(registerUserData);
        registerUser.setSettings(userSettings);
        UUID registerUserDataId = save(registerUserData);
        registerUserData.setId(registerUserDataId);

        UUID userSettingsId = save(userSettings);
        userSettings.setId(userSettingsId);

        registerUserData.setUser(registerUser);
        userSettings.setUserId(registerUser);

        registerUser.setUserData(registerUserData);
        registerUser.setSettings(userSettings);
        return persist(registerUser);
    }
}
