package org.write_day.domain.entities.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.*;
import org.write_day.domain.entities.message.dialogs.UserCommunity;
import org.write_day.domain.entities.images.user.ProfileImage;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user_table", indexes = {
        @Index(name = User.NICKNAME + "_index",  columnList = User.NICKNAME),
        @Index(name = User.USERNAME + "_index",  columnList = User.USERNAME)
})
@DynamicUpdate
@DynamicInsert
public class User {

    public static final String ID = "id";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String NICKNAME = "nickname";
    public static final String ROLE = "role";
    public static final String USER_DATA = "userdata_id";

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = ID)
    @Type(type="pg-uuid")
    private UUID id;

    /** Логин */
    @Column(name = USERNAME, unique = true, nullable = false, length = 32)
    private String username;

    /** Пароль */
    @JsonIgnore
    @Column(name = PASSWORD, nullable = false)
    private String password;

    /** Ник */
    @Column(name = NICKNAME, nullable = false, length = 32)
    private String nickname;

    /** Роль */
    @JsonIgnore
    @Column(name = ROLE, nullable = false, length = 24)
    private String role;

    @JsonManagedReference
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = USER_DATA)
    private UserData userData;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private UserSettings settings;

    @JsonManagedReference
    @OneToMany(mappedBy = ProfileImage.USER, fetch = FetchType.LAZY, cascade = javax.persistence.CascadeType.ALL, orphanRemoval = true)
    private List<ProfileImage> profileImage = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = UserCommunity.USER, fetch = FetchType.LAZY, cascade = javax.persistence.CascadeType.ALL, orphanRemoval = true)
    private List<UserCommunity> userCommunityList = new ArrayList<>();

    public ProfileImage getProfileImage() {
        if (profileImage.iterator().hasNext()) {
            return profileImage.iterator().next();
        } else {
            return null;
        }
    }

    public void setProfileImage(ProfileImage profileImage) {
        this.profileImage.clear();
        this.profileImage.add(profileImage);
    }

    public User() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserSettings getSettings() {
        return settings;
    }

    public void setSettings(UserSettings settings) {
        this.settings = settings;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
