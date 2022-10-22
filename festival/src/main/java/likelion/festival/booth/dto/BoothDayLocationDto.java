package likelion.festival.booth.dto;

import com.sun.istack.NotNull;
import likelion.festival.booth.entity.BoothType;
import likelion.festival.image.entity.Image;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class BoothDayLocationDto {

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

    private Long likeCnt;

    private Boolean isLike;

    private List<Image> images;
}
