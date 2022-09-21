package likelion.festival.dto;

import com.sun.istack.NotNull;
import likelion.festival.entitiy.NotificationType;
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

    @CreatedDate
    private LocalDateTime createdDateTime;
    @LastModifiedDate
    private LocalDateTime modifiedDateTime;

}
