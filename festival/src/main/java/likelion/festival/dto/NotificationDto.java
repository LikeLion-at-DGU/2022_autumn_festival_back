package likelion.festival.dto;

import com.sun.istack.NotNull;
import likelion.festival.entitiy.NotificationType;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;


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

    private long imageId;

    @CreatedDate
    private LocalDateTime createdDateTime;
    @LastModifiedDate
    private LocalDateTime modifiedDateTime;

    @Builder
    public NotificationDto(Long id, String title, String writer, String content, NotificationType notificationType, long imageId, LocalDateTime createdDateTime, LocalDateTime modifiedDateTime) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.notificationType = notificationType;
        this.imageId = imageId;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
    }
}
