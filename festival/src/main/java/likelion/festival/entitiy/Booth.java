package likelion.festival.entitiy;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Booth {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String content;

    private String notice;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BoothType boothType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BoothLocation boothLocation;

    @NotNull
    private String introduction;

    @NotNull
    private String startAt;

    @NotNull
    private String endAt;

    @OneToMany(mappedBy = "booth")
    private List<Menu> menuList = new ArrayList<>();

    @OneToMany(mappedBy = "booth")
    private List<Likes> likes = new ArrayList<>();

    private Long imageId;

    /*
    TODO :  comment 필드 추가
     */
}
