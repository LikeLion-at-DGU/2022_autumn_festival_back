package likelion.festival.repository;

import likelion.festival.entitiy.Notification;
import likelion.festival.entitiy.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByNotificationType(NotificationType notificationType);
}
