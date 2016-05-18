package org.write_day.domain.entities.images.journal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.write_day.domain.entities.images.BaseImage;
import org.write_day.domain.entities.images.Image;
import org.write_day.domain.entities.journal.Journal;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity

@Table(name = "journal_logo_image_table")
public class JournalLogoImage extends BaseImage implements Image {

    public static final String JOURNAL = "journal";

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
