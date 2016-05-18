package org.write_day.domain.entities.images.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.write_day.domain.entities.images.BaseImage;
import org.write_day.domain.entities.images.Image;
import org.write_day.domain.entities.post.UserPost;
import org.write_day.domain.entities.user.User;

import javax.persistence.*;

@Entity

@Table(name = "user_image_table")
public class UserPostImage extends BaseImage implements Image {

    public static final String USER = "userId";
    public static final String POST = "post";

    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = USER)
    private User userId;

    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = POST)
    private UserPost post;

}
