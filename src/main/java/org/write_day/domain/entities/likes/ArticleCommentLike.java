package org.write_day.domain.entities.likes;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.write_day.domain.entities.comments.ArticleComment;

import javax.persistence.*;

/**
 * Created by LucidMinds on 13.05.16.
 * package org.write_day.domain.entities.likes;
 */
@Entity
@Table(name = "article_comment_like_table")
@DynamicUpdate
@DynamicInsert
public class ArticleCommentLike extends Like {

    public static final String ARTICLE_COMMENT = "articleComment";

    @JsonManagedReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = ARTICLE_COMMENT)
    private ArticleComment articleComment;
}
