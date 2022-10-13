package likelion.festival.notification.dto;

import com.sun.istack.NotNull;
import likelion.festival.image.entity.Image;
import likelion.festival.notification.entity.Notification;
import likelion.festival.notification.entity.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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

}
