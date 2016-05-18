package org.write_day.services.message;

import org.write_day.components.enums.CommandStatus;
import org.write_day.components.enums.message.MessageStatus;
import org.write_day.components.enums.message.UserMessageRole;
import org.write_day.components.enums.message.UserMessageStatus;
import org.write_day.components.enums.types.MessageType;
import org.write_day.components.utils.UserMessageUtils;
import org.write_day.dao.message.UserMessageDAO;
import org.write_day.domain.entities.images.Image;
import org.write_day.domain.entities.message.dialogs.Dialog;
import org.write_day.domain.entities.message.user.UserImageMessage;
import org.write_day.domain.entities.message.user.UserMessage;
import org.write_day.domain.entities.message.user.UserTextMessage;
import org.write_day.domain.entities.user.User;
import org.write_day.services.BaseService;
import org.write_day.services.friend.FriendUtils;
import org.write_day.services.user.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserMessageService extends BaseService {

    @Autowired
    UserMessageDAO userMessageDao;
    @Autowired
    UserMessageUtils userMessageUtils;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    UserMessageUtils messageUtils;
    @Autowired
    DialogService dialogService;
    @Autowired
    FriendUtils friendUtils;

    public List<UserMessage> getUserMessages(UUID uuid) {
        return getUserMessages(uuid, 0);
    }

    /**
     * Получить сообщении пользователя
     * @param uuid - id диалога
     * @param number - с какого эл.
     * */
    public List<UserMessage> getUserMessages(UUID uuid, int number) {
        try {
            User currentUser = userDetailsService.getCurrentUser();
            Dialog dialog = findById(Dialog.class, uuid);
            UserMessageRole messageStatus = userMessageUtils.getCurrentUserRole(currentUser, dialog);
            if (messageStatus == UserMessageRole.FIRST_USER) {
                return userMessageDao.getDialogMessages(dialog, MessageStatus.ACTIVATE.toString(), max, number);
            }
            if (messageStatus == UserMessageRole.SECOND_USER) {
                return userMessageDao.getDialogMessages(dialog,MessageStatus.ACTIVATE.toString(), max, number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * Отправка сообщения
     * @param receiver - получатель
     * @param text - текст сообщения
     * */
    public CommandStatus sendTextMessage(User receiver, String text) {
        User sender = userDetailsService.getCurrentUser();
        return sendTextMessage(sender, receiver, text);
    }

    /**
     * Отправка сообщения
     * Перед отправкой проверяет id получателья и id отправителя,
     * если совпадают возвращает CURRENT_USER
     * потом ищет диалог пользователей, если их нет то создаст новый диалог
     * создает сообщение и отправляет сообщение
     * @param sender - отправитель
     * @param receiver - получатель
     * @param text - текст сообщения
     * */
    public CommandStatus sendTextMessage(User sender, User receiver, String text) {
        if (sender.getId().equals(receiver.getId())) {
            return CommandStatus.CURRENT_USER;
        }
        if (!friendUtils.isFriend(sender, receiver)) {
            return CommandStatus.ACCESS_DENIED;
        }
        Dialog dialog = dialogService.dialogDao.getDialogByUsers(sender, receiver);
        if (dialog == null) {
            dialog = dialogService.createDialog(sender, receiver);
        }
        UUID uuid = save(createTextMessage(sender, receiver, dialog, text));
        if (uuid != null) {
            return CommandStatus.SUCCESS;
        } else return CommandStatus.ERROR;
    }

    public CommandStatus sendImageMessage(User receiver, Image image) {
        User sender = userDetailsService.getCurrentUser();
        return sendImageMessage(sender, receiver, image);
    }

    public CommandStatus sendImageMessage(User sender, User receiver, Image image) {
        if (sender.getId().equals(receiver.getId())) {
            return CommandStatus.CURRENT_USER;
        }
        if (!friendUtils.isFriend(sender, receiver)) {
            return CommandStatus.ACCESS_DENIED;
        }
        Dialog dialog = dialogService.dialogDao.getDialogByUsers(sender, receiver);
        if (dialog == null) {
            dialog = dialogService.createDialog(sender, receiver);
        }
        UUID uuid = save(createImageMessage(sender, receiver, dialog, image));
        if (uuid != null) {
            return CommandStatus.SUCCESS;
        } else return CommandStatus.ERROR;
    }

    public CommandStatus deleteMessage(UUID message_id) {
        return deleteMessage(message_id, userDetailsService.getCurrentUser());
    }

    /**
     * Удалить сообщение
     * Пометить сообщение как удаленный,
     * Если у обоих пемечен как удаленный, удалить с БД
     * @param user - user
     * @param uuid - id сообщения
     * */
    public CommandStatus deleteMessage(UUID uuid, User user) {
        try {
            UserMessage message = findById(UserMessage.class, uuid);
            if (message == null) {
                return CommandStatus.NULL;
            }
            switch (messageUtils.getCurrentUserRole(message, user)) {
                case OTHER:
                    return CommandStatus.ACCESS_DENIED;
            }

            if (message.getReceiverStatus().equals(MessageStatus.DELETE.toString())) {
                if (message.getSenderStatus().equals(MessageStatus.DELETE.toString())) {
                    return delete(message);
                }
                message.setReceiverStatus(MessageStatus.DELETE.toString());
                return update(message);
            }
            if (message.getSenderStatus().equals(MessageStatus.DELETE.toString())) {
                if (message.getReceiverStatus().equals(MessageStatus.DELETE.toString())) {
                    return delete(message);
                }
                message.setSenderStatus(MessageStatus.DELETE.toString());
                return update(message);
            }
            return CommandStatus.ERROR;
        } catch (Exception e) {
            e.printStackTrace();
            return CommandStatus.ERROR;
        }
    }

    /**
     * Создает сообщение между пользователями
     * @param sender - отправитель
     * @param receiver - получатель
     * @param dialog - Диалог
     * @param text - текст
     * */
    public UserTextMessage createTextMessage(User sender, User receiver, Dialog dialog, String text) {
        UserTextMessage userMessage = new UserTextMessage();
        userMessage.setDate(new Date());
        userMessage.setDialog(dialog);
        userMessage.setReceiver(receiver);
        userMessage.setSender(sender);
        userMessage.setReceiverStatus(MessageStatus.ACTIVATE.toString());
        userMessage.setSenderStatus(MessageStatus.ACTIVATE.toString());
        userMessage.setStatus(UserMessageStatus.SEND.toString());
        userMessage.setType(MessageType.TEXT.toString());
        userMessage.setText(text);
        return userMessage;
    }

    public UserImageMessage createImageMessage(User sender, User receiver, Dialog dialog, Image image) {
        UserImageMessage userMessage = new UserImageMessage();
        userMessage.setDate(new Date());
        userMessage.setDialog(dialog);
        userMessage.setReceiver(receiver);
        userMessage.setSender(sender);
        userMessage.setReceiverStatus(MessageStatus.ACTIVATE.toString());
        userMessage.setSenderStatus(MessageStatus.ACTIVATE.toString());
        userMessage.setStatus(UserMessageStatus.SEND.toString());
        userMessage.setType(MessageType.TEXT.toString());
        userMessage.setFullDirectory(image.getFullDirectory());
        userMessage.setFullImage(image.getFullImage());
        userMessage.setImageCategory(image.getImageCategory());
        userMessage.setMiniature(image.getMiniature());
        return userMessage;
    }
}
