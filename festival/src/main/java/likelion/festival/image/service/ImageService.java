package likelion.festival.image.service;

import likelion.festival.image.dto.ImageDto;
import likelion.festival.booth.entity.Booth;
import likelion.festival.notification.entity.Notification;
import likelion.festival.image.repository.ImageRepository;
import likelion.festival.global.util.MD5Generator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Transactional
    public void saveNotificationImage(List<MultipartFile> itemImgList, Notification notification) {
        try {
            for (MultipartFile image : itemImgList) {
                String origFilename = image.getOriginalFilename();
                String servFilename = new MD5Generator(origFilename).toString();
                String savePath = System.getProperty("user.dir") + "/src/main/resources/";

                ImageDto imageDto = imageSaveSetting(image, origFilename, servFilename, savePath);
                imageDto.setNotification(notification);
                imageRepository.save(imageDto.toEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void saveBoothImage(List<MultipartFile> itemImgList, Booth booth) {
        try {
            for (MultipartFile image : itemImgList) {
                String origFilename = image.getOriginalFilename();
                String servFilename = new MD5Generator(origFilename).toString();
                String savePath = System.getProperty("user.dir") + "/src/main/resources/";

                ImageDto imageDto = imageSaveSetting(image, origFilename, servFilename, savePath);
                imageDto.setBooth(booth);
                imageRepository.save(imageDto.toEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ImageDto imageSaveSetting(MultipartFile image, String origFilename, String servFilename, String savePath) throws IOException {
        if (!new File(savePath).exists()) {
            try {
                new File(savePath).mkdir();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
        String imagePath = savePath + "/static/" + servFilename + ".jpg";
        String responsePath = "/static/" + servFilename + ".jpg";
        image.transferTo(new File(imagePath));
        ImageDto imageDto = new ImageDto();
        imageDto.setOriginFileName(origFilename);
        imageDto.setServerFileName(servFilename);
        imageDto.setStoredFilePath(responsePath);

        return imageDto;
    }
}