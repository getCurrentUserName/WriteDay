package org.write_day.domain.entities.post;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.write_day.domain.entities.comments.PostComment;
import org.write_day.domain.entities.images.user.UserPostImage;
import org.write_day.domain.entities.likes.PostLike;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LucidMinds on 12.05.16.
 * package org.write_day.domain.entities.post;
 */
@Entity
@Table(name = "user_post_table")
public class UserPost extends Post {

    public static final String TEXT = "text";

    @Column(name = TEXT, nullable = false)
    private String text;

    @OneToMany(mappedBy = PostLike.USER_POST, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLike> postLikes = new ArrayList<>();

    /** Комментарии */
    @JsonBackReference
    @OneToMany(mappedBy = PostComment.POST, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostComment> comments = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = UserPostImage.POST, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserPostImage> images = new ArrayList<>();


}
