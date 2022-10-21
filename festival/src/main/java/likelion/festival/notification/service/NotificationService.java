package likelion.festival.notification.service;

import java.util.stream.Collectors;
import likelion.festival.notification.dto.NotificationRequestDto;
import likelion.festival.notification.dto.NotificationResponseDto;
import likelion.festival.notification.entity.Notification;
import likelion.festival.notification.entity.NotificationType;
import likelion.festival.global.exception.WrongNotificationId;
import likelion.festival.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class NotificationService {

  private final NotificationRepository notificationRepository;

  public NotificationResponseDto readNotification(Long id) {
    Notification notification = findNotification(id);
    NotificationResponseDto notificationResponseDto = notification.toDto();
    return notificationResponseDto;
  }

  public List<NotificationResponseDto> readNotificationAll(NotificationType notificationType) {
    List<Notification> notifications = findNotifications(notificationType);
    return notifications.stream()
        .map(notification -> notification.toDto())
        .collect(Collectors.toList());
  }

  @Transactional
  public Notification createNotification(NotificationRequestDto notificationRequestDto) {
    Notification notification = notificationRequestDto.toEntity();
    return notificationRepository.save(notification);
  }

  @Transactional
  public void deleteNotification(Long id) {
    notificationRepository.deleteById(id);
  }

  @Transactional
  public Notification updateNotification(Long id, NotificationRequestDto notificationRequestDto) {
    Notification notification = findNotification(id);
    Notification newNotification = notificationRequestDto.toEntity();
    notification.updateNotification(newNotification);
    return notificationRepository.save(notification);
  }

  private Notification findNotification(Long id){
    return notificationRepository.findById(id).orElseThrow(() -> new WrongNotificationId());
  }
  private List<Notification> findNotifications(NotificationType notificationType){
    if (notificationType == null) {
      return notificationRepository.findAll();
    }
    return notificationRepository.findByNotificationType(notificationType);
  }
}
