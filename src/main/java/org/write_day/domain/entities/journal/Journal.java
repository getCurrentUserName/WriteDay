package org.write_day.domain.entities.journal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.write_day.domain.entities.images.journal.ArticleImage;
import org.write_day.domain.entities.images.journal.JournalLogoImage;
import org.write_day.domain.entities.message.JournalMessage;
import org.write_day.domain.entities.post.Article;
import org.write_day.domain.entities.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "journal_table")
public class Journal {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String ADMIN = "admin";

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = ID)
    @Type(type="pg-uuid")
    private UUID id;

    @Column(name = NAME, nullable = false)
    private String name;

    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = ADMIN)
    private User admin;

    @JsonManagedReference
    @OneToMany(mappedBy = JournalLogoImage.JOURNAL, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JournalLogoImage> logoImage = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = ArticleImage.JOURNAL, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleImage> journalImages = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = JournalUser.JOURNAL, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JournalUser> journalUsers = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = Article.JOURNAL, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> articles = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = JournalMessage.JOURNAL, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JournalMessage> journalMessages = new ArrayList<>();

    public Journal() {
    }

    public Journal(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public List<ArticleImage> getJournalImages() {
        return journalImages;
    }

    public void setJournalImages(List<ArticleImage> journalImages) {
        this.journalImages = journalImages;
    }

    public List<JournalUser> getJournalUsers() {
        return journalUsers;
    }

    public void setJournalUsers(List<JournalUser> admins) {
        this.journalUsers = admins;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public List<JournalMessage> getJournalMessages() {
        return journalMessages;
    }

    public void setJournalMessages(List<JournalMessage> journalMessages) {
        this.journalMessages = journalMessages;
    }

    public JournalLogoImage getLogoImage() {
        if (logoImage.iterator().hasNext()) {
            return logoImage.iterator().next();
        } else {
            return null;
        }
    }

    public void setLogoImage(JournalLogoImage logo_image) {
        this.logoImage.clear();
        this.logoImage.add(logo_image);
    }
}
