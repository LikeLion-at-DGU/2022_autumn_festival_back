package likelion.festival.dto;

import com.sun.istack.NotNull;
import likelion.festival.entitiy.Image;
import likelion.festival.entitiy.Notification;
import likelion.festival.entitiy.NotificationType;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private List<Image> images;

    @CreatedDate
    private LocalDateTime createdDateTime;
    @LastModifiedDate
    private LocalDateTime modifiedDateTime;

    @Builder
    public NotificationDto(Long id, String title, String writer, String content, NotificationType notificationType, List<Image> images,LocalDateTime createdDateTime, LocalDateTime modifiedDateTime) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.notificationType = notificationType;
        this.images = images;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
    }

    public Notification toEntity(){
        Notification build = Notification.builder()
                .id(id)
                .title(title)
                .writer(writer)
                .content(content)
                .notificationType(notificationType)
                .images(images)
                .build();
        return build;
    }

}
