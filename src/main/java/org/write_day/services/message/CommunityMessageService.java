package org.write_day.services.message;

import org.write_day.components.enums.CommandStatus;
import org.write_day.components.enums.message.UserMessageStatus;
import org.write_day.components.utils.CommunityUtils;
import org.write_day.dao.message.CommunityDAO;
import org.write_day.domain.entities.message.dialogs.Community;
import org.write_day.domain.entities.message.community.CommunityTextMessage;
import org.write_day.domain.entities.message.Message;
import org.write_day.domain.entities.user.User;
import org.write_day.services.BaseService;
import org.write_day.services.user.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CommunityMessageService extends BaseService {

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    CommunityUtils communityUtils;
    @Autowired
    CommunityDAO communityDAO;

    /**
     * Получить сообщении сообщества
     * */
    public List<List<Message>> getMessages(User user, int number) {
        List<Community> communities = communityUtils.getUserCommunities(user);
        List<List<Message>> messages = new ArrayList<>();
        for (Community community : communities) {
            messages.add(communityDAO.getMessagesByCommunity(community, max, number));
        }
        return messages;
    }

    /**
     * Отправка сообщение группе
     * @param sendUser - отправитель
     * @param community - получатель
     * @param text - текст сообщения
     * */
    public CommandStatus sendMessage(User sendUser, Community community, String text) {
        if (!communityUtils.isCommunity(sendUser, community)) {
            return CommandStatus.ACCESS_DENIED;
        }
        UUID uuid = save(createTextMessage(sendUser, community, text));
        if (uuid != null) {
            return CommandStatus.SUCCESS;
        } else return CommandStatus.ERROR;
    }

    /**
     * Создает сообщение для группы
     * @param sendUser - отправитель
     * @param community - получатель
     * @param text - текст
     * */
    public CommunityTextMessage createTextMessage(User sendUser, Community community, String text) {
        CommunityTextMessage communityTextMessage = new CommunityTextMessage();
        communityTextMessage.setDate(new Date());
        communityTextMessage.setCommunity(community);
        communityTextMessage.setSender(sendUser);
        communityTextMessage.setStatus(UserMessageStatus.SEND);
        communityTextMessage.setText(text);
        return communityTextMessage;
    }
}
