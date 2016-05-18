package org.write_day.domain.entities.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.write_day.components.enums.message.UserMessageStatus;
import org.write_day.domain.entities.message.dialogs.Community;
import org.write_day.domain.entities.message.dialogs.Dialog;
import org.write_day.domain.entities.user.User;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DynamicUpdate
@DynamicInsert
public abstract class Message {

    public static final String ID = "id";
    public static final String TEXT = "text";
    public static final String TYPE = "type";
    public static final String DATE = "date";
    public static final String STATUS = "status";
    public static final String SENDER = "sender";
    public static final String DIALOG = "dialog";
    public static final String COMMUNITY = "community";

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = ID)
    @Type(type="pg-uuid")
    private UUID id;

    /** Тип сообщения */
    @Column(name = TYPE, nullable = false)
    private String type;

    /** Дата сообщения */
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    @Column(name = DATE, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    /** Статус сообщения (Доставлен, прочтент итд) */
    @Column(name = STATUS, nullable = false)
    private String status;

    /** Отправляющий пользователь */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = SENDER)
    private User sender;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setStatus(UserMessageStatus status) {
        this.setStatus(status.name());
    }

    public Message() {
    }
}
