package org.write_day.components.utils;

import org.write_day.components.enums.CommandStatus;
import org.write_day.components.enums.types.FileTypes;
import org.write_day.components.exceptions.OtherFormatException;
import org.write_day.domain.entities.images.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

@Component
@PropertySource(value = { "classpath:app.properties" })
public class ImageUtils {

    @Autowired
    Environment env;
    Logger logger = Logger.getLogger(ImageUtils.class.getName());

    /** Записывает файл на диске */
    public CommandStatus save(byte[] bytes, File file) {
        try {
            File dir = file.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(bytes);
            bufferedOutputStream.close();
        } catch (Exception e) {
            return CommandStatus.ERROR;
        }
        return CommandStatus.OK;
    }

    /** Меняет размер изображения */
    @Async
    public void processImage(int maxWidth, int maxHeight, BufferedImage bufferedImage, File file, FileTypes fileType) throws ServletException, IOException {
        double max;
        int size;
        int width = maxWidth - bufferedImage.getWidth();
        int height = maxHeight - bufferedImage.getHeight();

        if (width<0 || height<0) {
            if(width < height) {
                max = maxWidth;
                size = bufferedImage.getWidth();
            } else {
                max = maxHeight;
                size = bufferedImage.getHeight();
            }
            if(size > 0 && size > max) {
                double trans=1.0/(size/max);

                AffineTransform transform = new AffineTransform();
                transform.scale(trans, trans);
                AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
                Double w = bufferedImage.getWidth() * trans;
                Double h = bufferedImage.getHeight() * trans;
                BufferedImage newBufferedImage = new BufferedImage(w.intValue(), h.intValue(), bufferedImage.getType());
                op.filter(bufferedImage, newBufferedImage);
                switch (fileType) {
                    case png:
                        ImageIO.write(newBufferedImage, "png", file);
                        break;
                    case jpeg:
                        ImageIO.write(newBufferedImage, "jpeg", file);
                        break;
                    case jpg:
                        ImageIO.write(newBufferedImage, "jpg", file);
                        break;
                    case gif:
                        ImageIO.write(newBufferedImage, "gif", file);
                        break;
                }
            }
        }
    }

    /** Удаляет изображение с диска */
    @Async
    public void deleteImage(Image image) {
        File fullImage = new File(env.getProperty("image.directory") + image.getFullDirectory() + image.getFullImage());
        File miniature = new File(env.getProperty("image.directory") + image.getFullDirectory() + image.getMiniature());
        if (fullImage.exists()) {
            fullImage.delete();
        }
        if (miniature.exists()) {
            miniature.delete();
        }
    }

    /** проверяет формат */
    public BufferedImage formatValidate(MultipartFile multipartFile) throws OtherFormatException {
        BufferedImage img;
        try {
            img = ImageIO.read(multipartFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new OtherFormatException();
        }
        FileTypes format = getType(multipartFile.getOriginalFilename());
        if (format == FileTypes.OTHER) {
            throw new OtherFormatException();
        }
        return img;
    }

    /** Получить тип изображении */
    public FileTypes getType(String type) {
        String name = type.substring(type.length() - 4, type.length());
        switch (name.toLowerCase()) {
            case ".jpg":
                return FileTypes.jpg;
            case ".png":
                return FileTypes.png;
            case ".gif":
                return FileTypes.gif;
            case "jpeg":
                if (type.substring(type.length() - 5, type.length()).equalsIgnoreCase(".jpeg")) {
                    return FileTypes.jpeg;
                }
            default:
                return FileTypes.OTHER;
        }
    }

    public ImageUtils() {
    }
}
