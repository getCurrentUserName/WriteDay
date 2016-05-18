package org.write_day.domain.entities.likes;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.write_day.domain.entities.comments.PostComment;

import javax.persistence.*;

/**
 * Created by LucidMinds on 13.05.16.
 * package org.write_day.domain.entities.likes;
 */
@Entity
@Table(name = "post_comment_like_table")
@DynamicUpdate
@DynamicInsert
public class PostCommentLike extends Like {

    public static final String POST_COMMENT = "postComment";

    @JsonManagedReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = POST_COMMENT)
    private PostComment postComment;
}
