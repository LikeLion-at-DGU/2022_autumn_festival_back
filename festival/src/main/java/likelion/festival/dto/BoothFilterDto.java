package likelion.festival.dto;

import com.sun.istack.NotNull;
import likelion.festival.entitiy.BoothType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BoothFilterDto {

    private Long id;

    @NotNull
    private BoothType boothType;

    @NotNull
    private String title;

    @NotNull
    private String introduction;

    @NotNull
    private String location;

    private Integer boothNo;


    private boolean active;
    /*
    TODO : 이미지 필드 및 좋아요 개수 필드 추가
     */
}
