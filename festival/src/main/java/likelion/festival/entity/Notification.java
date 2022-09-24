package likelion.festival.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Data
public class Notification extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String writer;
    @NotNull
    private String content;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @OneToMany(mappedBy = "notification",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Image> images = new ArrayList<>();

    @Builder
    public Notification(Long id, String title, String writer, String content, NotificationType notificationType, List<Image> images) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.notificationType = notificationType;
        this.images = images;
    }

}
