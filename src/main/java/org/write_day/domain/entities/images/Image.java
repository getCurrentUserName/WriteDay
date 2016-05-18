package org.write_day.domain.entities.images;


import java.util.Date;
import java.util.UUID;

public interface Image {

    UUID getId();
    String getFullDirectory();
    String getMiniature();
    String getFullImage();
    String getImageCategory();
    Date getDate();

    void setId(UUID uuid);
    void setFullDirectory(String string);
    void setMiniature(String string);
    void setFullImage(String string);
    void setImageCategory(String string);
    void setDate(Date date);

}
