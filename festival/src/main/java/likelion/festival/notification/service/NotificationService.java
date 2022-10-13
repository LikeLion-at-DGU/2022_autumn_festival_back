package likelion.festival.notification.service;

import likelion.festival.notification.dto.NotificationDto;
import likelion.festival.notification.entity.Notification;
import likelion.festival.notification.entity.NotificationType;
import likelion.festival.global.exception.WrongNotificationId;
import likelion.festival.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class NotificationService {

  private final NotificationRepository notificationRepository;

  public NotificationDto readNotification(Long id) {
    Optional<Notification> notificationOptional = notificationRepository.findById(id);
    if (notificationOptional.isEmpty()) {
      throw new WrongNotificationId();
    }

    Notification notification = notificationOptional.get();
    NotificationDto notificationDto = toDto(notification);

    return notificationDto;
  }

  public List<NotificationDto> readNotificationAll(NotificationType notificationType) {
    ArrayList<NotificationDto> notificationDtos = new ArrayList<>();
    if (notificationType == null) {
      ArrayList<Notification> notifications = notificationRepository.findAll();
      for (Notification notification : notifications) {
        NotificationDto notificationDto = toDto(notification);
        notificationDtos.add(notificationDto);
      }
      return notificationDtos;
    }
    ArrayList<Notification> notifications = notificationRepository.findByNotificationType(
        notificationType);
    for (Notification notification : notifications) {
      NotificationDto notificationDto = toDto(notification);
      notificationDtos.add(notificationDto);
    }
    return notificationDtos;
  }

  @Transactional
  public Notification createNotification(NotificationDto notificationDto) {
    Notification notification = toEntity(notificationDto);
    return notificationRepository.save(notification);
  }

  @Transactional
  public void deleteNotification(Long id) {
    notificationRepository.deleteById(id);
  }

  @Transactional
  public Notification updateNotification(Long id, NotificationDto notificationDto) {
    Optional<Notification> notificationOptional = notificationRepository.findById(id);
    if (notificationOptional.isEmpty()) {
      throw new WrongNotificationId();
    }
    Notification notification = notificationOptional.get();
    notification.updateNotification(notificationDto.getTitle(), notificationDto.getWriter(),
        notificationDto.getContent(), notificationDto.getNotificationType(),
        notificationDto.getImages());
    return notificationRepository.save(notification);
  }

  private NotificationDto toDto(Notification notification) {
    NotificationDto build = NotificationDto.builder()
        .id(notification.getId())
        .title(notification.getTitle())
        .writer(notification.getWriter())
        .content(notification.getContent())
        .notificationType(notification.getNotificationType())
        .images(notification.getImages())
        .createdDateTime(notification.getCreatedDateTime())
        .modifiedDateTime(notification.getModifiedDateTime())
        .build();
    return build;
  }

  private Notification toEntity(NotificationDto notificationDto) {
    Notification build = Notification.builder()
        .id(notificationDto.getId())
        .title(notificationDto.getTitle())
        .writer(notificationDto.getWriter())
        .content(notificationDto.getContent())
        .notificationType(notificationDto.getNotificationType())
        .images(notificationDto.getImages())
        .build();
    return build;
  }
}
