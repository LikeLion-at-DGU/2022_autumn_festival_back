package likelion.festival.notification.dto;

import likelion.festival.image.entity.Image;
import likelion.festival.notification.entity.Notification;
import likelion.festival.notification.entity.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequestDto {

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

  public Notification toEntity(NotificationRequestDto notificationRequestDto) {
    Notification build = Notification.builder()
        .id(notificationRequestDto.getId())
        .title(notificationRequestDto.getTitle())
        .writer(notificationRequestDto.getWriter())
        .content(notificationRequestDto.getContent())
        .notificationType(notificationRequestDto.getNotificationType())
        .images(notificationRequestDto.getImages())
        .build();
    return build;
  }

}
