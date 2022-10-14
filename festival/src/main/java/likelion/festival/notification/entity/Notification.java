package likelion.festival.notification.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import likelion.festival.global.baseEntity.BaseEntity;
import likelion.festival.image.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
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

  public void updateNotification(String title, String writer, String content,NotificationType notificationType, List<Image> images) {
    this.title = title;
    this.writer = writer;
    this.content = content;
    this.notificationType = notificationType;
    this.images = images;
  }
}
