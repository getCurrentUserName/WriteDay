package org.write_day.domain.entities.message.dialogs;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.write_day.domain.entities.user.User;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Связь группа пользователь, роль, дата вступления
 * */
@Entity
@Table(name = "user_community_table")
@DynamicUpdate
@DynamicInsert
public class UserCommunity {

    public static final String ID = "id";
    public static final String DATE = "date";
    public static final String ROLE = "role";
    public static final String USER = "userId";
    public static final String COMMUNITY = "community";

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = ID)
    @Type(type="pg-uuid")
    private UUID id;

    /** Роль */
    @Column(name = ROLE)
    private String role;

    /** Дата вступления в сообщества */
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    @Column(name = DATE, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    /** ПОЛЬЗОВАТЕЛЬ */
    @JsonManagedReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = USER)
    private User userId;

    /** Сообщества */
    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = COMMUNITY)
    private Community community;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User user) {
        this.userId = user;
    }

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }
}
