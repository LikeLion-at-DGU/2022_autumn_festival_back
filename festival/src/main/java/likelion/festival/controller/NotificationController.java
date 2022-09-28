package likelion.festival.controller;

import likelion.festival.dto.NotificationDto;
import likelion.festival.entity.Notification;
import likelion.festival.entity.NotificationType;
import likelion.festival.service.ImageService;
import likelion.festival.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping(value = "${app-notification}")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final ImageService imageService;

    @ResponseBody
    @GetMapping("{id}")
    public NotificationDto readNotification(@PathVariable Long id){
        return notificationService.readNotification(id);
    }

    @ResponseBody
    @GetMapping
    public ResponseEntity readNotificationAll(@RequestParam(required = false) NotificationType notificationType){
        return ResponseEntity.ok(notificationService.readNotificationAll(notificationType));
    }

    @ResponseBody
    @PostMapping
    public Integer createNotification(@RequestPart(value = "imgList",required = false) List<MultipartFile> imgList,
                                      @RequestParam(value = "notification", required = false) NotificationDto notificationDto){
        Notification notification = notificationService.createNotification(notificationDto);
        if (imgList==null){
            return HttpStatus.OK.value();
        }
        imageService.saveNotificationImage(imgList, notification);
        return HttpStatus.OK.value();
    }

    @ResponseBody
    @DeleteMapping("{id}")
    public String deleteNotification(@PathVariable Long id){
        notificationService.deleteNotification(id);
        return "Ok";
    }

    @ResponseBody
    @PutMapping("{id}")
    public ResponseEntity<Notification> updateNotification(@RequestBody NotificationDto request, @PathVariable Long id){
        return ResponseEntity.ok(notificationService.updateNotification(id, request));
    }

}
