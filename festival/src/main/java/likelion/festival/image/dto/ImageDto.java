package likelion.festival.image.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import likelion.festival.booth.entity.Booth;
import likelion.festival.image.entity.Image;
import likelion.festival.notification.entity.Notification;
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