package org.write_day.domain.entities.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by LucidMinds on 15.05.16.
 * package org.write_day.domain.entities.user;
 */
@Entity
@Table(name = "user_settings_table")
@DynamicUpdate
@DynamicInsert
public class UserSettings {

    public static final String ID = "id";
    public static final String MESSAGE_ACCESS = "messageAccess";
    public static final String USER = "userId";

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = ID)
    @Type(type="pg-uuid")
    @JsonIgnore
    private UUID id;

    /** Доступ к личным сообщениям */
    @Column(name = MESSAGE_ACCESS, length = 32)
    private String messageAccess;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "settings", optional = true)
    private User userId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getMessageAccess() {
        return messageAccess;
    }

    public void setMessageAccess(String messageAccess) {
        this.messageAccess = messageAccess;
    }
}
