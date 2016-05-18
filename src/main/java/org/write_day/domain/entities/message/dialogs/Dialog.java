package org.write_day.domain.entities.message.dialogs;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.write_day.domain.entities.message.user.UserMessage;
import org.write_day.domain.entities.message.user.UserTextMessage;
import org.write_day.domain.entities.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Диалог между двумя пользователями
 * */
@Entity
@Table(name = "dialog_table")
@DynamicUpdate
@DynamicInsert
public class Dialog {

    public static final String ID = "id";
    public static final String DATE = "date";
    public static final String FIRST_USER = "firstUser";
    public static final String SECOND_USER = "secondUser";
    public static final String FIRST_USER_STATUS = "firstUserStatus";
    public static final String SECOND_USER_STATUS = "secondUserStatus";

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = ID)
    @Type(type="pg-uuid")
    private UUID id;

    /** Статус диалога 1 пользователя */
    @JsonIgnore
    @Column(name = FIRST_USER_STATUS, nullable = false)
    private String firstUserStatus;

    /** Статус диалога 2 пользователя */
    @JsonIgnore
    @Column(name = SECOND_USER_STATUS, nullable = false)
    private String secondUserStatus;

    /** Дата создания диалога */
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    @Column(name = DATE, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    /** 1 пользователь */
    @JsonManagedReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = FIRST_USER)
    private User firstUser;

    /** 2 пользователь */
    @JsonManagedReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = SECOND_USER)
    private User secondUser;

    @JsonBackReference
    @OneToMany(mappedBy = UserTextMessage.DIALOG, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserMessage> userMessages = new ArrayList<>();

    public Dialog() {
    }

    public Dialog(User sendUser, User getUser) {
        this.firstUser = sendUser;
        this.secondUser = getUser;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstUserStatus() {
        return firstUserStatus;
    }

    public void setFirstUserStatus(String firstUserStatus) {
        this.firstUserStatus = firstUserStatus;
    }

    public String getSecondUserStatus() {
        return secondUserStatus;
    }

    public void setSecondUserStatus(String secondUserStatus) {
        this.secondUserStatus = secondUserStatus;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(User firstUser) {
        this.firstUser = firstUser;
    }

    public User getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(User secondUser) {
        this.secondUser = secondUser;
    }

    public List<UserMessage> getUserMessages() {
        return userMessages;
    }

    public void setUserMessages(List<UserMessage> messages) {
        this.userMessages = messages;
    }
}
