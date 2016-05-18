package org.write_day.domain.entities.images;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created by LucidMinds on 12.05.16.
 * package org.write_day.domain.entities.images;
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DynamicUpdate
@DynamicInsert
public abstract class BaseImage implements Image {

    public static final String ID = "id";
    public static final String DATE = "date";
    public static final String DESCRIPTION = "description";
    public static final String MINIATURE = "miniature";
    public static final String FULL_IMAGE = "full_image";
    public static final String IMAGE_DIRECTORY = "image_directory";
    public static final String IMAGE_CATEGORY = "image_category";
    public static final String USER = "user_id";

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = ID)
    @Type(type="pg-uuid")
    private UUID id;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm")
    @Column(name = DATE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = DESCRIPTION)
    private String description;

    /** Миниатюра */
    @Column(name = MINIATURE)
    private String miniature;

    /** Полное изображение */
    @Column(name = FULL_IMAGE)
    private String fullImage;

    /** Путь к файлу У */
    @Column(name = IMAGE_DIRECTORY)
    private String fullDirectory;

    /** Категория изображении */
    @Column(name = IMAGE_CATEGORY)
    private String imageCategory;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMiniature() {
        return miniature;
    }

    public void setMiniature(String miniature) {
        this.miniature = miniature;
    }

    public String getFullImage() {
        return fullImage;
    }

    public void setFullImage(String full_image) {
        this.fullImage = full_image;
    }

    public String getFullDirectory() {
        return fullDirectory;
    }

    public void setFullDirectory(String full_directory) {
        this.fullDirectory = full_directory;
    }

    public String getImageCategory() {
        return imageCategory;
    }

    public void setImageCategory(String image_category) {
        this.imageCategory = image_category;
    }
}
