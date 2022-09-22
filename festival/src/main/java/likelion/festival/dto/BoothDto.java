package likelion.festival.dto;

import com.sun.istack.NotNull;
import likelion.festival.entitiy.BoothLocation;
import likelion.festival.entitiy.BoothType;
import lombok.Builder;
import lombok.Data;


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
    private BoothLocation boothLocation;

    private String notice;

    @NotNull
    private String content;

    @NotNull
    private String startAt;

    @NotNull
    private String endAt;

    private Long imageId;

    @Builder
    public BoothDto(Long id, String title, String introduction, BoothType boothType, BoothLocation boothLocation, String notice, String content, String startAt, String endAt, Long imageId) {
        this.id = id;
        this.title = title;
        this.introduction = introduction;
        this.boothType = boothType;
        this.boothLocation = boothLocation;
        this.notice = notice;
        this.content = content;
        this.startAt = startAt;
        this.endAt = endAt;
        this.imageId = imageId;
    }

    //TODO : 위치 이미지, 이미지 필드 추가
}
