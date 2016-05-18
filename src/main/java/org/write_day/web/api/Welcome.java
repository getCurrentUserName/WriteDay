package org.write_day.web.api;

import org.write_day.domain.dto.ResponseResult;
import org.write_day.components.enums.CommandStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class Welcome {

    /**
     * Статус авторизации
     * @param principal
     * @return result : OK - авторизован, result : ANONYM - не авторизован
     *  */
    @RequestMapping(value = "status")
    public ResponseResult status(Principal principal) {
        if (principal != null) {
            return new ResponseResult(CommandStatus.OK);
        }
        return new ResponseResult(CommandStatus.ANONYM);
    }

    /**
     * Нет доступа
     * @return ACCESS_DENIED
     * */
    @RequestMapping(value = "404")
    public ResponseResult accessDenied() {
        return new ResponseResult(CommandStatus.ACCESS_DENIED);
    }
}
