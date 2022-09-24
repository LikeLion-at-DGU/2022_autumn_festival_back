package likelion.festival.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    private Long imageId;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;





    /*
        TODO : 이미지 필드 추가
    */
}
