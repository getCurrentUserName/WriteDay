package org.write_day.domain.entities.comments;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.write_day.domain.entities.user.User;
import org.write_day.domain.entities.post.UserPost;

import javax.persistence.*;

@Entity
@Table(name = "user_post_comment_table")
public class PostComment extends Comment {

    /** Запись пользователя */
    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = POST)
    private UserPost post;

}
