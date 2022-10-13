package likelion.festival.notification.repository;

import likelion.festival.notification.entity.Notification;
import likelion.festival.notification.entity.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

  @Override
  ArrayList<Notification> findAll();

  ArrayList<Notification> findByNotificationType(NotificationType notificationType);
}
