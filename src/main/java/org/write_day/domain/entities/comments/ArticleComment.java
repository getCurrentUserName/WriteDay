package org.write_day.domain.entities.comments;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.write_day.domain.entities.post.Article;
import org.write_day.domain.entities.user.User;

import javax.persistence.*;

@Entity

@Table(name = "article_comment_table")
public class ArticleComment extends Comment {

    /** Статья */
    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = ARTICLE)
    private Article article;

}
