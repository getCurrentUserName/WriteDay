package org.write_day.domain.entities.post;

import javax.persistence.*;

/**
 * Created by LucidMinds on 13.05.16.
 * package org.write_day.domain.entities.post;
 */
@Entity
@Table(name = "user_repost_table")
public class UserRepost extends Post {

    public static final String POST = "post";
    public static final String TEXT = "text";

    @Column(name = TEXT, nullable = false)
    private String text;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = POST)
    private Post post;
}
