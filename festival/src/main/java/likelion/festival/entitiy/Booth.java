package likelion.festival.entitiy;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Booth {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotNull
    private String Title;

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

    /*
    TODO : 이미지 필드 추가
     */
}
