package org.write_day.domain.entities.images.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.write_day.domain.entities.images.BaseImage;
import org.write_day.domain.entities.images.Image;
import org.write_day.domain.entities.user.User;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Изображении профиля пользователя
 * */
@Entity
@Table(name = "profile_image_table")
@DynamicUpdate
@DynamicInsert
public class ProfileImage extends BaseImage implements Image {

    public static final String ID = "id";
    public static final String DATE = "date";
    public static final String USER = "userId";

    /** Пользователь */
    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = USER)
    private User userId;

    public User getUserId() {
        return userId;
    }

    public void setUserId(User user) {
        this.userId = user;
    }
}
