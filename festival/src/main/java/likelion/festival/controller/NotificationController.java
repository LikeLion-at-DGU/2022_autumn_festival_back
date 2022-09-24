package likelion.festival.controller;

import likelion.festival.dto.NotificationDto;
import likelion.festival.entitiy.Notification;
import likelion.festival.entitiy.NotificationType;
import likelion.festival.service.ImageService;
import likelion.festival.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    public Integer createNotification(@RequestPart(value = "imgList") List<MultipartFile> imgList, @RequestParam(value = "notification") NotificationDto notificationDto){

        Notification notification = notificationService.createNotification(notificationDto);
        if (imgList==null){
            return HttpStatus.OK.value();
        }
        imageService.saveNotificationImage(imgList, notification);
        return HttpStatus.OK.value();
    }

    @DeleteMapping("{id}")
    public Integer deleteNotification(@PathVariable Long id){
        notificationService.deleteNotification(id);
        return HttpStatus.OK.value();
    }

    @PutMapping("{id}")
    public ResponseEntity<Notification> updateNotification(
            @RequestBody NotificationDto request, @PathVariable Long id
    ){
        return ResponseEntity.ok(notificationService.updateNotification(id, request));
    }

}
