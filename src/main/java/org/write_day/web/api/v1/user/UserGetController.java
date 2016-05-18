package org.write_day.web.api.v1.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.write_day.components.enums.user.Roles;
import org.write_day.domain.entities.MethodRunTime;
import org.write_day.domain.entities.user.User;
import org.write_day.services.BaseService;
import org.write_day.services.user.UserDetailsServiceImpl;

/**
 * Created by LucidMinds on 15.05.16.
 * package org.write_day.web.api.v1.user;
 */
@RestController
@Secured({Roles.ROLE_ADMIN, Roles.ROLE_USER, Roles.ROLE_EDITOR, Roles.ROLE_SUPER_ADMIN})
public class UserGetController {

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    BaseService baseService;

    @RequestMapping(value = "profile", method = RequestMethod.GET)
    public User profile() {
        long start = System.currentTimeMillis();
        User user = userDetailsService.getUserProfile();
        long finish = System.currentTimeMillis();
        baseService.asyncPersist(new MethodRunTime("org.write_day.web.api.v1.user",
                "UserGetController",
                "profile",
                (finish - start)));
        return user;
    }
}
