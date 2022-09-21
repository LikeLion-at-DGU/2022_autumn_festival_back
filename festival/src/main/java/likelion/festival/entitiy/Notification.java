package likelion.festival.entitiy;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@NoArgsConstructor
public class Notification extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String writer;
    @NotNull
    private String content;
    @Enumerated(EnumType.ORDINAL)
    private NotificationType notificationType;





    /*
        TODO : 이미지 필드 추가
    */
}
