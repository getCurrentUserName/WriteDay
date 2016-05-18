package org.write_day.domain.entities.journal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.write_day.domain.entities.user.User;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "journal_user_table")
public class JournalUser {

    public static final String ID = "id";
    public static final String ROLE = "role";
    public static final String STATUS = "status";
    public static final String USER = "userId";
    public static final String JOURNAL = "journal";

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = ID)
    @Type(type="pg-uuid")
    private UUID id;

    @Column(name = ROLE, nullable = false)
    private String role;

    @Column(name = STATUS, nullable = false)
    private String status;

    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = JOURNAL)
    private Journal journal;

    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = USER)
    private User userId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public User getUser() {
        return userId;
    }

    public void setUser(User user) {
        this.userId = user;
    }
}
