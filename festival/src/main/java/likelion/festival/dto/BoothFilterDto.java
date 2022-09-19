package likelion.festival.dto;

import likelion.festival.entitiy.BoothLocation;
import likelion.festival.entitiy.BoothType;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class BoothFilterDto {

    private Long id;
    @NonNull
    private BoothType boothType;
    @NonNull
    private String title;
    @NonNull
    private String introduction;
    @NonNull
    private BoothLocation boothLocation;
    /*
    TODO : 이미지 필드 및 좋아요 개수 필드 추가
     */
}
