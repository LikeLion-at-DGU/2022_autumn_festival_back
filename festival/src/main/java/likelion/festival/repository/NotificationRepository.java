package likelion.festival.repository;

import likelion.festival.entity.Notification;
import likelion.festival.entity.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {

    @Override
    ArrayList<Notification> findAll();
    ArrayList<Notification> findByNotificationType(NotificationType notificationType);
}
