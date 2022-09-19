package likelion.festival.dto;

import com.sun.istack.NotNull;
import likelion.festival.entitiy.BoothLocation;
import likelion.festival.entitiy.BoothType;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class BoothDto {

    private Long id;

    @NonNull
    private String title;

    @NonNull
    private String introduction;

    @NotNull
    private BoothType boothType;

    @NonNull
    private BoothLocation boothLocation;

    private String notice;

    @NotNull
    private String content;

    @NonNull
    private String startAt;

    @NotNull
    private String endAt;

    //TODO : 위치 이미지, 이미지 필드 추가
}
