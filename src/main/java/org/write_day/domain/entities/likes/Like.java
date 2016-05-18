package org.write_day.domain.entities.likes;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.write_day.domain.entities.user.User;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by LucidMinds on 12.05.16.
 * package org.write_day.domain.entities.likes;
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DynamicUpdate
@DynamicInsert
public abstract class Like {

    public static final String ID = "id";
    public static final String VOTE = "userVote";
    public static final String USER = "userId";

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = ID)
    @Type(type="pg-uuid")
    private UUID id;

    @Column(name = VOTE, nullable = false)
    private String userVote;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = USER)
    private User userId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserVote() {
        return userVote;
    }

    public void setUserVote(String vote) {
        this.userVote = vote;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
}
