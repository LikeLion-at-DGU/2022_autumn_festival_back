package likelion.festival.dto;

import com.sun.istack.NotNull;
import likelion.festival.entity.Image;
import likelion.festival.entity.Notification;
import likelion.festival.entity.NotificationType;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class NotificationDto {

    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String writer;
    @NotNull
    private String content;
    @NotNull
    private NotificationType notificationType;
    @CreatedDate
    private LocalDateTime createdDateTime;
    @LastModifiedDate
    private LocalDateTime modifiedDateTime;

    private List<Image> images;

    @Builder
    public NotificationDto(Long id, String title, String writer, String content, NotificationType notificationType, LocalDateTime createdDateTime, LocalDateTime modifiedDateTime, List<Image> images) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.notificationType = notificationType;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
        this.images = images;
    }

}
