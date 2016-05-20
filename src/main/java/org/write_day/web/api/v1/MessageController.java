package org.write_day.web.api.v1;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.write_day.components.enums.user.Roles;
import org.write_day.domain.dto.ResponseResult;
import org.write_day.domain.entities.message.dialogs.Dialog;
import org.write_day.domain.entities.message.user.UserMessage;
import org.write_day.domain.entities.user.User;
import org.write_day.services.message.DialogService;
import org.write_day.services.message.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.write_day.services.user.UserService;

import java.util.List;
import java.util.UUID;

/**
 * <h1><b>Сообщении и диалоги</b></h1>
 * Created by LucidMinds on 12.05.16.
 * package org.write_day.web.api_v1.message; <br>
 */
@RestController
@Secured({Roles.ROLE_ADMIN, Roles.ROLE_USER, Roles.ROLE_EDITOR, Roles.ROLE_SUPER_ADMIN})
@RequestMapping(value = "api/v1")
public class MessageController {

    @Autowired
    UserService userService;
    @Autowired
    UserMessageService userMessageService;
    @Autowired
    DialogService dialogService;
    @Autowired
    Environment env;

    /**
     * <b>Получить сообщении диалога</b> - host/api/v1/dialog/{id}/messages <br>
     * <b>GET</b> запрос
     * @param id id диалога
     * @return сообщении диалога
     * */
    @RequestMapping(value = "dialog/{id}/messages", method = RequestMethod.GET)
    public List<UserMessage> inbox(@PathVariable(value = "id") UUID id) {
        return userMessageService.getUserMessages(id);
    }

    /**
     * <b>Получить сообщении диалога</b> - host/api/v1/dialog/{id}/messages/{number} <br>
     * <b>GET</b> запрос
     * @param number с какого эл. начать
     * @param id id диалога
     * @return сообщении диалога
     * */
    @RequestMapping(value = "{id}/messages/{number}", method = RequestMethod.GET)
    public List<UserMessage> inbox(@PathVariable(value = "id") UUID id,
                                       @PathVariable(value = "number") int number) {
        return userMessageService.getUserMessages(id, number);
    }

    /**
     * <b>Получить все диалоги</b> - host/api/v1/dialog/all <br>
     * <b>GET</b> запрос
     * @return Получить все диалоги
     * */
    @RequestMapping(value = "dialog/all", method = RequestMethod.GET)
    public List<Dialog> allDialog() {
        return dialogService.getUserDialogs();
    }


    /**
     * <b>Отправить сообщение</b>- host/api/v1/message/send <br>
     * <b>POST</b> запрос
     * @param id  id получателя
     * @param text текст сообщении
     * @return result : SUCCESS - успешно <br>
     *         result : ERROR - ошибка <br>
     *         result : ACCESS_DENIED - нет доступа <br>
     *
     * */
    @RequestMapping(value = "send/text", method = RequestMethod.POST)
    public ResponseResult send(@RequestParam(value = "id") UUID id,
                               @RequestParam(value = "text") String text) {
        User user = userService.findById(User.class, id);
        return new ResponseResult(userMessageService.sendTextMessage(user, text));
    }

/*    @RequestMapping(value = "send/image", method = RequestMethod.POST)
    public ResponseResult updateImage(@RequestParam(value = "image") MultipartFile image) {

        return new ResponseResult(profileImageService.setImage(image,
                current_user, env.getProperty("message.image.directory")));
    }*/

    /**
     * <b>Удалить сообщение</b>- host/api/v1/message/delete <br>
     * <b>POST</b> запрос
     * @param id id сообщения
     * @return result : SUCCESS - успешно <br>
     *         result : ERROR - ошибка <br>
     *         result : ACCESS_DENIED - нет доступа <br>
     * */
    @RequestMapping(value = "message/delete", method = RequestMethod.POST)
    public ResponseResult deleteMessage(@RequestParam(value = "id") UUID id) {
        return new ResponseResult(userMessageService.deleteMessage(id));
    }

    /**
     * <b>Удалить диалог</b> - host/api/v1/dialog/delete <br>
     * <b>POST</b> запрос
     * @param id id диалога
     * @return result : SUCCESS - успешно <br>
     *         result : ERROR - ошибка <br>
     *         result : ACCESS_DENIED - нет доступа <br>
     * */
    @RequestMapping(value = "dialog/delete", method = RequestMethod.POST)
    public ResponseResult deleteDialog(@RequestParam(value = "id") UUID id) {
        return new ResponseResult(dialogService.deleteDialog(id));
    }
}
