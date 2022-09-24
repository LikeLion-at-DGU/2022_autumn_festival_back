package likelion.festival.repository;

import likelion.festival.entity.Notification;
import likelion.festival.entity.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByNotificationType(NotificationType notificationType);
}
