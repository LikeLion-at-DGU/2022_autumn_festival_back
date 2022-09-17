package likelion.festival.dto;

import likelion.festival.entitiy.NotificationType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDto {
    private Long id;
    private String title;
    private String writer;
    private String content;
    private NotificationType notificationType;
    private LocalDateTime createdDateTime;
    private LocalDateTime modifiedDateTime;
}
