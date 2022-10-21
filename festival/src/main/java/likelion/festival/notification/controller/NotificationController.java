package likelion.festival.notification.controller;

import likelion.festival.notification.dto.NotificationRequestDto;
import likelion.festival.notification.dto.NotificationResponseDto;
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

  @GetMapping("{id}")
  public NotificationResponseDto readNotification(@PathVariable Long id) {
    return notificationService.readNotification(id);
  }

  @GetMapping
  public ResponseEntity readNotificationAll(@RequestParam(required = false) NotificationType notificationType) {
    return ResponseEntity.ok(notificationService.readNotificationAll(notificationType));
  }

  @PostMapping
  public Integer createNotification(@RequestBody NotificationRequestDto notificationRequestDto) {
    Notification notification = notificationService.createNotification(notificationRequestDto);
    return HttpStatus.OK.value();
  }

  @DeleteMapping("{id}")
  public HttpStatus deleteNotification(@PathVariable Long id) {
    notificationService.deleteNotification(id);
    return HttpStatus.NO_CONTENT;
  }

  @PutMapping("{id}")
  public ResponseEntity<Notification> updateNotification(@RequestBody NotificationRequestDto notificationRequestDto,
      @PathVariable Long id) {
    return ResponseEntity.ok(notificationService.updateNotification(id, notificationRequestDto));
  }

}
