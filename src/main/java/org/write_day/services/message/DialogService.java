package org.write_day.services.message;

import org.write_day.components.enums.CommandStatus;
import org.write_day.components.enums.message.MessageStatus;
import org.write_day.components.utils.UserMessageUtils;
import org.write_day.dao.message.DialogDAO;
import org.write_day.domain.entities.message.dialogs.Dialog;
import org.write_day.domain.entities.user.User;
import org.write_day.services.BaseService;
import org.write_day.services.user.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class DialogService extends BaseService {

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    DialogDAO dialogDao;
    @Autowired
    UserMessageUtils messageUtils;

    public CommandStatus deleteDialog(UUID dialog_id) {
        return deleteDialog(dialog_id, userDetailsService.getCurrentUser());
    }

    /**
     * Пометить диалог как удаленный,
     * Если у обоих пемечен как удаленный, удалить с БД
     * @param user - user
     * @param uuid - id диалога
     * */
    public CommandStatus deleteDialog(UUID uuid, User user) {
        Dialog dialog;
        try {
            dialog = findById(Dialog.class, uuid);
        } catch (Exception e) {
            return CommandStatus.ERROR;
        }
        if (dialog == null) {
            return CommandStatus.NULL;
        }
        switch (messageUtils.getCurrentUserRole(user, dialog)) {
            case FIRST_USER:
                dialog.setFirstUserStatus(MessageStatus.DELETE.toString());
                if (dialog.getSecondUserStatus().equals(MessageStatus.DELETE.toString())) {
                    return delete(dialog);
                }
                return update(dialog);
            case SECOND_USER:
                dialog.setSecondUserStatus(MessageStatus.DELETE.toString());
                if (dialog.getFirstUserStatus().equals(MessageStatus.DELETE.toString())) {
                    return delete(dialog);
                }
                return update(dialog);
        }
        return CommandStatus.ERROR;
    }


    /**
     * Получить диалоги пользователя
     * */
    public List<Dialog> getUserDialogs() {
        try {
            User currentUser = userDetailsService.getCurrentUser();
            return dialogDao.getUserDialogs(currentUser, MessageStatus.ACTIVATE.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Dialog createDialog(User sender, User receiver) {
        Dialog dialog = new Dialog();
        dialog.setDate(new Date());
        dialog.setFirstUserStatus(MessageStatus.ACTIVATE.toString());
        dialog.setSecondUserStatus(MessageStatus.ACTIVATE.toString());
        dialog.setFirstUser(sender);
        dialog.setSecondUser(receiver);
        UUID uuid = dialogDao.save(dialog);
        dialog.setId(uuid);
        return dialog;
    }

}
