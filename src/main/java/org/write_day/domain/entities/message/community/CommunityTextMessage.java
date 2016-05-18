package org.write_day.domain.entities.message.community;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Сообщение группы
 * */
@Entity
@Table(name = "community_messages_table")
@DynamicUpdate
@DynamicInsert
public class CommunityTextMessage extends CommunityMessage {

    /** Текст сообщения */
    @Column(name = TEXT, nullable = false)
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CommunityTextMessage() {
    }
}
