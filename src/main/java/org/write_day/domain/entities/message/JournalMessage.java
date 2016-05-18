package org.write_day.domain.entities.message;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.write_day.domain.entities.journal.Journal;
import org.write_day.domain.entities.user.User;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity

@Table(name = "journal_message_table")
public class JournalMessage extends Message {

    public static final String JOURNAL = "journal";
    public static final String USER_STATUS = "user_status";
    public static final String JOURNAL_STATUS = "journalStatus";

    /** СТАТУС УДАЛЕНИЯ */
    @JsonIgnore
    @Column(name = JOURNAL_STATUS)
    private Date journalStatus;

    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = JOURNAL)
    private Journal journal;

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }
}
