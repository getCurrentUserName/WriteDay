package org.write_day.domain.entities.post;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.write_day.domain.entities.comments.ArticleComment;
import org.write_day.domain.entities.images.journal.ArticleImage;
import org.write_day.domain.entities.journal.Journal;
import org.write_day.domain.entities.likes.ArticleLike;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/** ПОСТЫ ЖУРНАЛА */
@Entity
@Table(name = "article_table")
public class Article extends Post {

    public static final String TITLE = "title";
    public static final String BODY = "body";
    public static final String JOURNAL = "journal";

    @Column(name = TITLE, nullable = false)
    private String title;

    @Column(name = BODY)
    private String body;

    @JsonManagedReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = JOURNAL)
    private Journal journal;

    @OneToMany(mappedBy = ArticleLike.ARTICLE, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleLike> articleLikes = new ArrayList<>();

    /** Комментарии */
    @JsonBackReference
    @OneToMany(mappedBy = ArticleComment.ARTICLE, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleComment> comments = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = ArticleImage.ARTICLE, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleImage> images = new ArrayList<>();

}
