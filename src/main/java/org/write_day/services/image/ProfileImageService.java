package org.write_day.services.image;

import org.write_day.components.enums.CommandStatus;
import org.write_day.components.enums.types.FileTypes;
import org.write_day.components.exceptions.OtherFormatException;
import org.write_day.components.utils.ImageUtils;
import org.write_day.dao.user.ProfileImageDAO;
import org.write_day.domain.entities.images.user.ProfileImage;
import org.write_day.domain.entities.user.User;
import org.write_day.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;
import java.util.UUID;

@Service
@PropertySource(value = { "classpath:app.properties" })
public class ProfileImageService {

    @Autowired
    ProfileImageDAO profileImageDAO;
    @Autowired
    BaseService baseService;
    @Autowired
    ImageUtils imageUtils;
    @Autowired
    Environment env;

    public ProfileImage findByUser(User user) {
        return profileImageDAO.findByUser(user);
    }

    public CommandStatus setImage(MultipartFile multipartFile, User user, String category) {
        /** проверка на null */
        if (multipartFile == null) {
            return CommandStatus.EMPTY;
        }

        BufferedImage img;
        try {
            img = imageUtils.formatValidate(multipartFile);
        } catch (OtherFormatException e) {
            return CommandStatus.OTHER_FORMAT;
        }
        FileTypes format = imageUtils.getType(multipartFile.getOriginalFilename());

        /** СООТВЕТСВУЮЩИ ИНИЦИЛИЗИРУЕМ ПЕРЕМЕННУЮ */
        ProfileImage profileImage = getProfileImage(user);

        /** 1 ПАПКА ПО 1 СИМВОЛУ В ID */
        String firstDir = user.getId().toString().substring(1, 2) + "/";
        /** 2 ПАПКА ПО 2 СИМВОЛУ В ID */
        String secondDir = user.getId().toString().substring(0, 1) + "/";
        /** ПОЛНЫЙ ПУТЬ */
        String fullDir = category + firstDir + secondDir + user.getId() + "/";
        /** СОЗДАЕМ ИМЯ ИЗОБРАЖЕНИИ */
        String fullImageName = UUID.randomUUID().toString() + "." + format.toString();
        /** СОЗДАЕМ ИМЯ МИНИАТЮРЫ */
        String miniatureName = UUID.randomUUID().toString() + "." + format.toString();
        try {
            /** ЧИТАЕМ КАК БАЙТЫ */
            byte[] bytes = multipartFile.getBytes();
            /** СОЗДАЕМ ПОЛНОЕ ИЗОБРАЖЕНИЕ */
            File full = new File(env.getProperty("image.directory") + fullDir + fullImageName);
            /** СОЗДАЕМ МИНИАТЮРУ */
            File miniature = new File(env.getProperty("image.directory") + fullDir + miniatureName);

            /** ЗАПИСЫВАЕМ МИНИТЮРУ НА ДИСК (ПОЛНОЕ ИЗОБРАЖЕНИЕ) */
            if (imageUtils.save(bytes, miniature) == CommandStatus.ERROR) {
                return CommandStatus.ERROR;
            }
            /** МЕНЯЕМ РАЗМЕР СОХРАНЕННОГО ФАЙЛА */
            imageUtils.processImage(100, 100, img, miniature, format);

            if (imageUtils.save(bytes, full) == CommandStatus.ERROR) {
                return CommandStatus.ERROR;
            }

            profileImage.setUserId(user);
            profileImage.setDate(new Date());
            profileImage.setImageCategory(category);
            profileImage.setFullDirectory(fullDir);
            profileImage.setFullImage(fullImageName);
            profileImage.setMiniature(miniatureName);
            /** ПРОВЕРКА НА ДОБАВЛЕНИЕ ИЛИ ОБНОВЛЕНИЕ */
            if (user.getProfileImage() == null) {
                return baseService.persist(profileImage);
            } else {
                profileImage.setId(user.getProfileImage().getId());
                return baseService.update(profileImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CommandStatus.ERROR;
        }

    }

    private ProfileImage getProfileImage(User user) {
        ProfileImage profileImage;
        if (user.getProfileImage() != null) {
            imageUtils.deleteImage(user.getProfileImage());
            profileImage = user.getProfileImage();
        } else {
            profileImage = new ProfileImage();
        }
        return profileImage;
    }
}
