package org.write_day.domain.entities.likes;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.write_day.domain.entities.post.Article;

import javax.persistence.*;

/**
 * Created by LucidMinds on 12.05.16.
 * package org.write_day.domain.entities.likes;
 */
@Entity
@Table(name = "article_like_table")
@DynamicUpdate
@DynamicInsert
public class ArticleLike extends Like {

    public static final String ARTICLE = "article";

    @JsonManagedReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = ARTICLE)
    private Article article;
}
