package likelion.festival.notification.controller;

import likelion.festival.notification.dto.NotificationDto;
import likelion.festival.notification.entity.Notification;
import likelion.festival.notification.entity.NotificationType;
import likelion.festival.image.service.ImageService;
import likelion.festival.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "api/notification")
@RequiredArgsConstructor
public class NotificationController {

  private final NotificationService notificationService;
  private final ImageService imageService;

  @GetMapping("{id}")
  public NotificationDto readNotification(@PathVariable Long id) {
    return notificationService.readNotification(id);
  }

  @GetMapping
  public ResponseEntity readNotificationAll(
      @RequestParam(required = false) NotificationType notificationType) {
    return ResponseEntity.ok(notificationService.readNotificationAll(notificationType));
  }

  @PostMapping
  public Integer createNotification(
      @RequestPart(value = "imgList", required = false) List<MultipartFile> imgList,
      @RequestParam(value = "notification", required = false) NotificationDto notificationDto) {
    Notification notification = notificationService.createNotification(notificationDto);
    if (imgList == null) {
      return HttpStatus.OK.value();
    }
    imageService.saveNotificationImage(imgList, notification);
    return HttpStatus.OK.value();
  }

  @DeleteMapping("{id}")
  public String deleteNotification(@PathVariable Long id) {
    notificationService.deleteNotification(id);
    return "Ok";
  }

  @PutMapping("{id}")
  public ResponseEntity<Notification> updateNotification(
      @RequestPart(value = "imgList", required = false) List<MultipartFile> imgList,
      @RequestParam(value = "notification", required = false) NotificationDto notificationDto,
      @PathVariable Long id) {
    return ResponseEntity.ok(notificationService.updateNotification(id, notificationDto));
  }

}
