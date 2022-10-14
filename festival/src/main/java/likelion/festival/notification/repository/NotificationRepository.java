package likelion.festival.notification.repository;

import java.util.List;
import likelion.festival.notification.entity.Notification;
import likelion.festival.notification.entity.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
  List<Notification> findByNotificationType(NotificationType notificationType);
}
