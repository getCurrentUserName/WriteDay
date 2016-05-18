package org.write_day.domain.entities.images.journal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.write_day.domain.entities.images.BaseImage;
import org.write_day.domain.entities.images.Image;
import org.write_day.domain.entities.post.Article;
import org.write_day.domain.entities.journal.Journal;

import javax.persistence.*;

@Entity

@Table(name = "journal_image_table")
public class ArticleImage extends BaseImage implements Image {

    public static final String JOURNAL = "journal";
    public static final String ARTICLE = "article";

    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = JOURNAL)
    private Journal journal;

    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = ARTICLE)
    private Article article;

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
