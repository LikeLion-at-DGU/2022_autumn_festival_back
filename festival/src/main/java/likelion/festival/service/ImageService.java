package likelion.festival.service;

import likelion.festival.dto.ImageDto;
import likelion.festival.entity.Booth;
import likelion.festival.entity.Notification;
import likelion.festival.repository.ImageRepository;
import likelion.festival.util.MD5Generator;
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
                String savePath = System.getProperty("user.dir") + "/src/main/resources/static/";

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
                String savePath = System.getProperty("user.dir") + "/src/main/resources/static/";

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
        String imagePath = savePath + "/" + servFilename + ".jpg";
        image.transferTo(new File(imagePath));
        ImageDto imageDto = new ImageDto();
        imageDto.setOriginFileName(origFilename);
        imageDto.setServerFileName(servFilename);
        imageDto.setStoredFilePath(imagePath);

        return imageDto;
    }
}