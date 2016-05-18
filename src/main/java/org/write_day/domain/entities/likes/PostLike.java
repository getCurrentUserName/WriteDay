package org.write_day.domain.entities.likes;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.write_day.domain.entities.post.UserPost;

import javax.persistence.*;

/**
 * Created by LucidMinds on 12.05.16.
 * package org.write_day.domain.entities.likes;
 */
@Entity
@Table(name = "post_like_table")
@DynamicUpdate
@DynamicInsert
public class PostLike extends Like {

    public static final String USER_POST = "userPost";

    @JsonManagedReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = USER_POST)
    private UserPost userPost;
}
