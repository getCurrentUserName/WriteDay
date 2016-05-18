package org.write_day.domain.entities.message.community;

import org.write_day.domain.entities.images.Image;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Сообщении группы в формате изображения
 * */
@Entity
@Table(name = "community_image_message_table")
@DynamicUpdate
@DynamicInsert
public class CommunityImageMessage extends CommunityMessage implements Image {

    public static final String MINIATURE = "miniature";
    public static final String FULL_IMAGE = "full_image";
    public static final String IMAGE_DIRECTORY = "image_directory";
    public static final String IMAGE_CATEGORY = "image_category";

    /** Миниатюра */
    @Column(name = MINIATURE)
    private String miniature;

    /** Имя полной изображении */
    @Column(name = FULL_IMAGE)
    private String fullImage;

    /** Путь к файлу */
    @Column(name = IMAGE_DIRECTORY)
    private String fullDirectory;

    /** Категория изображении */
    @Column(name = IMAGE_CATEGORY)
    private String imageCategory;

    @Override
    public String getMiniature() {
        return miniature;
    }

    @Override
    public void setMiniature(String miniature) {
        this.miniature = miniature;
    }

    @Override
    public String getFullImage() {
        return fullImage;
    }

    @Override
    public void setFullImage(String fullImage) {
        this.fullImage = fullImage;
    }

    @Override
    public String getFullDirectory() {
        return fullDirectory;
    }

    @Override
    public void setFullDirectory(String fullDirectory) {
        this.fullDirectory = fullDirectory;
    }

    @Override
    public String getImageCategory() {
        return imageCategory;
    }

    @Override
    public void setImageCategory(String imageCategory) {
        this.imageCategory = imageCategory;
    }

}
