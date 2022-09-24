package likelion.festival.dto;

import com.sun.istack.NotNull;
import likelion.festival.entitiy.BoothType;
import likelion.festival.entitiy.Image;
import lombok.Builder;
import lombok.Data;

import java.util.List;

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

    private Integer boothNo;

    private String notice;

    @NotNull
    private String content;

    @NotNull
    private String startAt;

    @NotNull
    private String endAt;

    private List<Image> images;

    @Builder
    public BoothDto(Long id, String title, String introduction, BoothType boothType, String location, Integer boothNo, String notice, String content, String startAt, String endAt, List<Image> images) {
        this.id = id;
        this.title = title;
        this.introduction = introduction;
        this.boothType = boothType;
        this.location = location;
        this.boothNo = boothNo;
        this.notice = notice;
        this.content = content;
        this.startAt = startAt;
        this.endAt = endAt;
        this.images =images;
    }

    //TODO : 위치 이미지, 이미지 필드 추가
}
