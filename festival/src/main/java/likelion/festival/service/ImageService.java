package likelion.festival.service;

import likelion.festival.dto.ImageDto;
import likelion.festival.entity.Booth;
import likelion.festival.entity.Image;
import likelion.festival.entity.Notification;
import likelion.festival.repository.BoothRepository;
import likelion.festival.repository.ImageRepository;
import likelion.festival.repository.NotificationRepository;
import likelion.festival.util.MD5Generator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final NotificationRepository notificationRepository;
    private final BoothRepository boothRepository;

    private final BoothService boothService;

    @Transactional
    public void saveNotificationImage(List<MultipartFile> itemImgList, Notification notification) {
        try {
            for (MultipartFile image : itemImgList) {
                String origFilename = image.getOriginalFilename();
                String servFilename = new MD5Generator(origFilename).toString();
                String savePath = System.getProperty("user.dir") + "/files";

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
                String savePath = System.getProperty("user.dir") + "/files";

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
                imageDto.setBooth(booth);
                imageRepository.save(imageDto.toEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ImageDto getImage(Long id) {
        Optional<Image> imageOptional = imageRepository.findById(id);
        if (!imageOptional.isPresent()) {
            throw new EntityNotFoundException("해당 공지사항이 없습니다");
        }
        Image image = imageOptional.get();

        ImageDto imageDto = ImageDto.builder()
                .id(id)
                .originFileName(image.getOriginFileName())
                .serverFileName(image.getServerFileName())
                .storedFilePath(image.getStoredFilePath())
                .build();
        return imageDto;
    }


}