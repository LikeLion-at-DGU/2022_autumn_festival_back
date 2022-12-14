package likelion.festival.service;

import likelion.festival.dto.NotificationDto;
import likelion.festival.entity.Notification;
import likelion.festival.entity.NotificationType;
import likelion.festival.exception.WrongNotificationId;
import likelion.festival.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationDto readNotification(Long id){
        Optional<Notification> notificationOptional = notificationRepository.findById(id);
        if (notificationOptional.isEmpty()){
            throw new WrongNotificationId();
        }

        Notification notification = notificationOptional.get();
        NotificationDto notificationDto = NotificationDto.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .writer(notification.getWriter())
                .content(notification.getContent())
                .notificationType(notification.getNotificationType())
                .images(notification.getImages())
                .createdDateTime(notification.getCreatedDateTime())
                .modifiedDateTime(notification.getModifiedDateTime())
                .build();

        return notificationDto;
    }

    public List<NotificationDto> readNotificationAll(NotificationType notificationType){
        ArrayList<NotificationDto> notificationDtos = new ArrayList<>();
        if (notificationType == null){
            ArrayList<Notification> notifications = notificationRepository.findAll();
            for (Notification notification: notifications){
                NotificationDto notificationDto = NotificationDto.builder()
                        .id(notification.getId())
                        .title(notification.getTitle())
                        .writer(notification.getWriter())
                        .content(notification.getContent())
                        .notificationType(notification.getNotificationType())
                        .images(notification.getImages())
                        .createdDateTime(notification.getCreatedDateTime())
                        .modifiedDateTime(notification.getModifiedDateTime())
                        .build();
                notificationDtos.add(notificationDto);
            }
            return  notificationDtos;
         }
        ArrayList<Notification> notifications = notificationRepository.findByNotificationType(notificationType);
        for (Notification notification: notifications){
            NotificationDto notificationDto = NotificationDto.builder()
                    .id(notification.getId())
                    .title(notification.getTitle())
                    .writer(notification.getWriter())
                    .content(notification.getContent())
                    .notificationType(notification.getNotificationType())
                    .images(notification.getImages())
                    .createdDateTime(notification.getCreatedDateTime())
                    .modifiedDateTime(notification.getModifiedDateTime())
                    .build();
            notificationDtos.add(notificationDto);
        }
        return notificationDtos;
    }

    @Transactional
    public Notification createNotification(NotificationDto notificationDto){
        Notification notification = new Notification();
        BeanUtils.copyProperties(notificationDto,notification);
        return notificationRepository.save(notification);
    }

    @Transactional
    public void deleteNotification(Long id){
        notificationRepository.deleteById(id);
    }

    @Transactional
    public Notification updateNotification(Long id, NotificationDto notificationDto){
        Optional<Notification> notification = notificationRepository.findById(id);
        if (notification.isEmpty()){
            throw new WrongNotificationId();
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
