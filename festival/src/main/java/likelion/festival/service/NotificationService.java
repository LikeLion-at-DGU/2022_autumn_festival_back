package likelion.festival.service;

import likelion.festival.dto.NotificationDto;
import likelion.festival.entity.Notification;
import likelion.festival.entity.NotificationType;
import likelion.festival.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;


    public NotificationDto readNotification(Long id){
        Optional<Notification> notificationOptional = notificationRepository.findById(id);
        if (!notificationOptional.isPresent()){
            throw new EntityNotFoundException("해당 공지사항이 없습니다");
        }

        Notification notification = notificationOptional.get();

        NotificationDto notificationDto = NotificationDto.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .writer(notification.getWriter())
                .content(notification.getContent())
                .notificationType(notification.getNotificationType())
                .imageId(notification.getImageId())
                .createdDateTime(notification.getCreatedDateTime())
                .modifiedDateTime(notification.getModifiedDateTime())
                .build();
        return notificationDto;
    }


    public List<Notification> readNotificationAll(NotificationType notificationType){
        if (notificationType == null){
            return notificationRepository.findAll();
        }
        return notificationRepository.findByNotificationType(notificationType);
    }

    @Transactional
    public Integer createNotification(NotificationDto notificationDto){
        Notification notification = new Notification();
        BeanUtils.copyProperties(notificationDto,notification);
        notificationRepository.save(notification);
        return HttpStatus.CREATED.value();

    }

    @Transactional
    public void deleteNotification(Long id){
        notificationRepository.deleteById(id);
    }

    @Transactional
    public Notification updateNotification(Long id, NotificationDto notificationDto){
        Optional<Notification> notification = notificationRepository.findById(id);
        if (!notification.isPresent()){
            throw new EntityNotFoundException("해당 공지사항이 없습니다");
        }
        Notification notification1 = notification.get();
        notification1.setTitle(notificationDto.getTitle());
        notification1.setWriter(notificationDto.getWriter());
        notification1.setContent(notificationDto.getContent());
        notification1.setNotificationType(notificationDto.getNotificationType());
        notification1.setModifiedDateTime(notificationDto.getModifiedDateTime());

        return notificationRepository.save(notification1);

    }

}
