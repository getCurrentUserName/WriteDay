package org.write_day.domain.entities.message.user;

import org.write_day.domain.entities.message.Message;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Сообщение диалога
 * */
@Entity

@Table(name = "user_text_message_table")
@DynamicUpdate
@DynamicInsert
public class UserTextMessage extends UserMessage {

    public static final String SENDER_STATUS = "senderStatus";
    public static final String RECEIVER_STATUS = "receiverStatus";
    public static final String RECEIVER = "receiver";
    public static final String DIALOG = "dialog";

    /** Текст сообщения */
    @Column(name = Message.TEXT, nullable = false)
    private String text;

    public UserTextMessage() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
