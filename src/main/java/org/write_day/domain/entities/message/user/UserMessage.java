package org.write_day.domain.entities.message.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.write_day.domain.entities.message.dialogs.Dialog;
import org.write_day.domain.entities.message.Message;
import org.write_day.domain.entities.user.User;

import javax.persistence.*;

/**
 * Created by LucidMinds on 12.05.16.
 * package org.write_day.domain.entities.message;
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DynamicUpdate
@DynamicInsert
public abstract class UserMessage extends Message {

    public static final String MINIATURE = "miniature";
    public static final String FULL_IMAGE = "full_image";
    public static final String IMAGE_DIRECTORY = "image_directory";
    public static final String IMAGE_CATEGORY = "image_category";
    public static final String SENDER_STATUS = "senderStatus";
    public static final String RECEIVER_STATUS = "receiverStatus";
    public static final String RECEIVER = "receiver";

    /** Статус отправляющего пользователя */
    @JsonIgnore
    @Column(name = SENDER_STATUS, nullable = false)
    private String senderStatus;

    /** Статус получающего пользователя */
    @JsonIgnore
    @Column(name = RECEIVER_STATUS, nullable = false)
    private String receiverStatus;

    /** Получающий пользователь */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = RECEIVER)
    private User receiver;

    /** Диалог */
    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = DIALOG)
    protected Dialog dialog;

    public String getSenderStatus() {
        return senderStatus;
    }

    public void setSenderStatus(String senderStatus) {
        this.senderStatus = senderStatus;
    }

    public String getReceiverStatus() {
        return receiverStatus;
    }

    public void setReceiverStatus(String receiverStatus) {
        this.receiverStatus = receiverStatus;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }
}
