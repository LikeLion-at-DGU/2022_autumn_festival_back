package likelion.festival.notification.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import likelion.festival.global.baseEntity.BaseEntity;
import likelion.festival.image.entity.Image;
import likelion.festival.notification.dto.NotificationResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Notification extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotNull
  private String title;
  @NotNull
  private String writer;
  @NotNull
  private String content;
  @Enumerated(EnumType.STRING)
  private NotificationType notificationType;

  @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL)
  @JsonIgnore
  private List<Image> images = new ArrayList<>();

  public void updateNotification(Notification notification) {
    this.title = notification.title;
    this.writer = notification.writer;
    this.content = notification.content;
    this.notificationType = notification.notificationType;
    this.images = notification.images;
  }

  public NotificationResponseDto toDto(){
    NotificationResponseDto build = NotificationResponseDto.builder()
        .id(id)
        .title(title)
        .writer(writer)
        .content(content)
        .notificationType(notificationType)
        .images(images)
        .createdDateTime(getCreatedDateTime())
        .modifiedDateTime(getModifiedDateTime())
        .build();
    return build;
  }

}
