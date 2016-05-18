package org.write_day.domain.entities.post;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.write_day.domain.entities.user.User;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created by LucidMinds on 13.05.16.
 * package org.write_day.domain.entities.post;
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DynamicUpdate
@DynamicInsert
public abstract class Post {

    public static final String ID = "id";
    public static final String DATE = "date";
    public static final String USER = "userId";

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = ID)
    @Type(type="pg-uuid")
    private UUID id;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    @Column(name = DATE, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = USER)
    private User userId;

}
