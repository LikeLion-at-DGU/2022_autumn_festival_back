package likelion.festival.dto;

import com.sun.istack.NotNull;
import likelion.festival.entitiy.BoothType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BoothDto {
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String introduction;

    @NotNull
    private BoothType boothType;

    @NotNull
    private String location;

    @NotNull
    private Integer boothNo;

    private String notice;

    @NotNull
    private String content;

    @NotNull
    private String startAt;

    @NotNull
    private String endAt;

    //TODO : 위치 이미지, 이미지 필드 추가
}
