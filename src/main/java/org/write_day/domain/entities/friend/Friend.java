package org.write_day.domain.entities.friend;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.write_day.domain.entities.user.User;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "friend_table")
public class Friend {

    public static final String ID = "id";
    public static final String FIRST_USER_STATUS = "firstUserStatus";
    public static final String SECOND_USER_STATUS = "secondUserStatus";
    public static final String FIRST_USER = "firstUser";
    public static final String SECOND_USER = "secondUser";

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = ID)
    @Type(type="pg-uuid")
    private UUID id;

    @Column(name = FIRST_USER_STATUS, nullable = false)
    private String firstUserStatus;

    @Column(name = SECOND_USER_STATUS, nullable = false)
    private String secondUserStatus;

    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = FIRST_USER)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private User firstUser;

    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = SECOND_USER)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private User secondUser;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstUserStatus() {
        return firstUserStatus;
    }

    public void setFirstUserStatus(String this_user_status) {
        this.firstUserStatus = this_user_status;
    }

    public String getSecondUserStatus() {
        return secondUserStatus;
    }

    public void setSecondUserStatus(String another_user_status) {
        this.secondUserStatus = another_user_status;
    }

    public User getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(User this_user) {
        this.firstUser = this_user;
    }

    public User getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(User another_user) {
        this.secondUser = another_user;
    }

}
