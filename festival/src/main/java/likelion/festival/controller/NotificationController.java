package likelion.festival.controller;

import likelion.festival.dto.ImageDto;
import likelion.festival.dto.NotificationDto;
import likelion.festival.entity.Notification;
import likelion.festival.entity.NotificationType;
import likelion.festival.service.ImageService;
import likelion.festival.service.NotificationService;
import likelion.festival.util.MD5Generator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping(value = "/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final ImageService imageService;

    @GetMapping("{id}")
    public NotificationDto readNotification(@PathVariable Long id){
        return notificationService.readNotification(id);
    }

    @GetMapping
    public ResponseEntity readNotificationAll(@RequestParam(required = false) NotificationType notificationType){
        return ResponseEntity.ok(notificationService.readNotificationAll(notificationType));
    }

    @PostMapping
    public Integer createNotification(@RequestPart(value = "images",required = false) MultipartFile images, @RequestParam(value = "notification") NotificationDto notificationDto){
        if (images==null){
            notificationService.createNotification(notificationDto);
            return HttpStatus.OK.value();
        }
        try {
            String origFilename = images.getOriginalFilename();
            String servFilename = new MD5Generator(origFilename).toString();

            String savePath =System.getProperty("user.dir")+"/files";


            if (!new File(savePath).exists()){
                try {
                    new File(savePath).mkdir();
                }
                catch (Exception e){
                    e.getStackTrace();
                }
            }
            String imagePath = savePath + "/" + servFilename+".jpg";
            images.transferTo(new File(imagePath));

            ImageDto imageDto = new ImageDto();
            imageDto.setOriginFileName(origFilename);
            imageDto.setServerFileName(servFilename);
            imageDto.setStoredFilePath(imagePath);

            Long imageId = imageService.saveImage(imageDto);
            notificationDto.setImageId(imageId);
            notificationService.createNotification(notificationDto);
        }catch (Exception e){
            e.printStackTrace();
        }
        return HttpStatus.OK.value();
    }

    @DeleteMapping("{id}")
    public String deleteNotification(@PathVariable Long id){
        notificationService.deleteNotification(id);
        return "Ok";
    }

    @PutMapping("{id}")
    public ResponseEntity<Notification> updateNotification(@RequestBody NotificationDto request, @PathVariable Long id){
        return ResponseEntity.ok(notificationService.updateNotification(id, request));
    }

}
