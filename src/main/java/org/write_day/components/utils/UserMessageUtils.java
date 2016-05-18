package org.write_day.components.utils;

import org.write_day.components.enums.message.UserMessageRole;
import org.write_day.domain.entities.message.dialogs.Dialog;
import org.write_day.domain.entities.message.user.UserMessage;
import org.write_day.domain.entities.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMessageUtils {

    /**
     * Опредеяет роль пользователья в сообщении
     * @param currentUser - текущий пользователь
     * @param userMessage - сообщение
     * */
    public UserMessageRole getCurrentUserRole(UserMessage userMessage, User currentUser) {
        User sendUser = userMessage.getReceiver();
        User getUser = userMessage.getSender();
        if (currentUser.getId().equals(sendUser.getId())) {
            return UserMessageRole.SENDER;
        }
        if (currentUser.getId().equals(getUser.getId())) {
            return UserMessageRole.RECEIVER;
        }
        return UserMessageRole.OTHER;
    }

    /**
     * Опредеяет номер пользователья в диалоге
     * @param currentUser - пользователь
     * @param dialog - диалог
     * */
    public UserMessageRole getCurrentUserRole(User currentUser, Dialog dialog) {
        User firstUser = dialog.getFirstUser();
        User secondUser = dialog.getSecondUser();
        if (firstUser.getId().equals(currentUser.getId())) {
            return UserMessageRole.FIRST_USER;
        }
        if (secondUser.getId().equals(currentUser.getId())) {
            return UserMessageRole.SECOND_USER;
        }
        return UserMessageRole.OTHER;
    }
}
