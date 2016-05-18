package org.write_day.domain.entities.message.community;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.write_day.domain.entities.message.dialogs.Community;
import org.write_day.domain.entities.message.Message;

import javax.persistence.*;

/**
 * Created by LucidMinds on 12.05.16.
 * package org.write_day.domain.entities.message;
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DynamicUpdate
@DynamicInsert
public abstract class CommunityMessage extends Message {

    /** Получающая сообщества */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = COMMUNITY)
    protected Community community;

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }
}
