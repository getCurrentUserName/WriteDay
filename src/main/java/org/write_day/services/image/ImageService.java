package org.write_day.services.image;

import org.write_day.components.enums.CommandStatus;
import org.write_day.components.utils.ImageUtils;
import org.write_day.dao.BaseDAO;
import org.write_day.domain.entities.images.Image;
import org.write_day.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService extends BaseService {

    @Autowired
    ImageUtils imageUtils;
    @Autowired
    BaseDAO baseDAO;

    /** Удаляем файлы */
    public CommandStatus delete(Image image) {
        try {
            if (baseDAO.delete(image) == CommandStatus.SUCCESS) {
                imageUtils.deleteImage(image);
                return CommandStatus.SUCCESS;
            }
            return CommandStatus.ERROR;
        } catch (Exception e) {
            e.printStackTrace();
            return CommandStatus.ERROR;
        }
    }
}
