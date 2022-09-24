package likelion.festival.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import likelion.festival.entitiy.Booth;
import likelion.festival.entitiy.Image;
import likelion.festival.entitiy.Notification;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ImageDto {

    private Long id;

    private String originFileName;

    private String serverFileName;

    private String storedFilePath;

    @JsonIgnore
    private Notification notification;

    @JsonIgnore
    private Booth booth;


    public Image toEntity(){
        Image build = Image.builder()
                .id(id)
                .originFileName(originFileName)
                .serverFileName(serverFileName)
                .storedFilePath(storedFilePath)
                .notification(notification)
                .booth(booth)
                .build();
        return build;
    }

    @Builder
    public ImageDto(Long id, String originFileName, String serverFileName, String storedFilePath, Notification notification, Booth booth) {
        this.id = id;
        this.originFileName = originFileName;
        this.serverFileName = serverFileName;
        this.storedFilePath = storedFilePath;
        this.notification = notification;
        this.booth = booth;
    }
}