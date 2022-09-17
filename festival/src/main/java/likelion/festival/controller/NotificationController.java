package likelion.festival.controller;

import likelion.festival.dto.NotificationDto;
import likelion.festival.entitiy.Notification;
import likelion.festival.entitiy.NotificationType;
import likelion.festival.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("{id}")
    public ResponseEntity<Notification> readNotification(@PathVariable Long id){
        return ResponseEntity.ok(notificationService.readNotification(id));
    }

    @GetMapping
    public ResponseEntity readNotificationAll(@RequestParam(required = false)NotificationType notificationType){
        return ResponseEntity.ok(notificationService.readNotificationAll(notificationType));
    }

    @PostMapping
    public Integer createNotification(@RequestBody NotificationDto request){
        return notificationService.createNotification(request);
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

    @GetMapping("/test")
    public String test(){
        return "성공";
    }
}
