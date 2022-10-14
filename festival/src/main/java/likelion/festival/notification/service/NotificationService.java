package likelion.festival.notification.service;

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
    Notification notification = notificationRepository.findById(id)
        .orElseThrow(() -> new WrongNotificationId());

    NotificationResponseDto notificationResponseDto = NotificationResponseDto.toDto(notification);

    return notificationResponseDto;
  }

  public List<NotificationResponseDto> readNotificationAll(NotificationType notificationType) {
    List<NotificationResponseDto> notificationResponseDtos = new ArrayList<>();
    if (notificationType == null) {
      List<Notification> notifications = notificationRepository.findAll();
      for (Notification notification : notifications) {
        NotificationResponseDto notificationResponseDto = NotificationResponseDto.toDto(notification);
        notificationResponseDtos.add(notificationResponseDto);
      }
      return notificationResponseDtos;
    }
    List<Notification> notifications = notificationRepository.findByNotificationType(
        notificationType);
    for (Notification notification : notifications) {
      NotificationResponseDto notificationResponseDto = NotificationResponseDto.toDto(notification);
      notificationResponseDtos.add(notificationResponseDto);
    }
    return notificationResponseDtos;
  }

  @Transactional
  public Notification createNotification(NotificationRequestDto notificationRequestDto) {
    Notification notification = notificationRequestDto.toEntity(notificationRequestDto);
    return notificationRepository.save(notification);
  }

  @Transactional
  public void deleteNotification(Long id) {
    notificationRepository.deleteById(id);
  }

  @Transactional
  public Notification updateNotification(Long id, NotificationRequestDto notificationRequestDto) {
    Notification notification = notificationRepository.findById(id)
        .orElseThrow(() -> new WrongNotificationId());


    notification.updateNotification(notificationRequestDto.getTitle(), notificationRequestDto.getWriter(),
        notificationRequestDto.getContent(), notificationRequestDto.getNotificationType(),
        notificationRequestDto.getImages());
    return notificationRepository.save(notification);
  }
}
