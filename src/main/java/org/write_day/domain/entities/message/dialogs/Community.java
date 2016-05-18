package org.write_day.domain.entities.message.dialogs;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.write_day.domain.entities.message.community.CommunityMessage;
import org.write_day.domain.entities.message.community.CommunityTextMessage;
import org.write_day.domain.entities.message.Message;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Группа
 * */
@Entity
@Table(name = "community_table")
@DynamicUpdate
@DynamicInsert
public class Community {

    public static final String ID = "id";
    public static final String DATE = "date";
    public static final String NAME = "name";

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = ID)
    @Type(type="pg-uuid")
    private UUID id;

    /** Дата создания сообщества */
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    @Column(name = DATE, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    /** Дата создания сообщества */
    @Column(name = NAME, nullable = false)
    private String name;

    /** Список пользователей сообщества */
    @JsonBackReference
    @OneToMany(mappedBy = UserCommunity.COMMUNITY, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCommunity> userCommunityList = new ArrayList<>();

    /** Сообщении сообщества */
    @JsonBackReference
    @OneToMany(mappedBy = Message.COMMUNITY, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunityMessage> communityMessages = new ArrayList<>();

    public Community() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CommunityMessage> getCommunityMessages() {
        return communityMessages;
    }

    public void setCommunityMessages(List<CommunityMessage> communityMessages) {
        this.communityMessages = communityMessages;
    }

    public void addCommunityMessage(CommunityTextMessage communityMessage) {
        this.communityMessages.add(communityMessage);
    }

    public List<UserCommunity> getUserCommunityList() {
        return userCommunityList;
    }

    public void setUserCommunityList(List<UserCommunity> userCommunityList) {
        this.userCommunityList = userCommunityList;
    }
}
