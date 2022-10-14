package likelion.festival.notification.dto;

import java.time.LocalDateTime;
import java.util.List;
import likelion.festival.image.entity.Image;
import likelion.festival.notification.entity.Notification;
import likelion.festival.notification.entity.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponseDto {

  private Long id;
  private String title;
  private String writer;
  private String content;
  private NotificationType notificationType;
  @CreatedDate
  private LocalDateTime createdDateTime;
  @LastModifiedDate
  private LocalDateTime modifiedDateTime;
  private List<Image> images;

  public static NotificationResponseDto toDto(Notification notification) {
    NotificationResponseDto build = NotificationResponseDto.builder()
        .id(notification.getId())
        .title(notification.getTitle())
        .writer(notification.getWriter())
        .content(notification.getContent())
        .notificationType(notification.getNotificationType())
        .images(notification.getImages())
        .createdDateTime(notification.getCreatedDateTime())
        .modifiedDateTime(notification.getModifiedDateTime())
        .build();
    return build;
  }

}
