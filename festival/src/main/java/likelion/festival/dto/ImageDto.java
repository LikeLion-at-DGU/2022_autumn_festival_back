package likelion.festival.dto;

import likelion.festival.entitiy.Image;
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


    public Image toEntity(){
        Image build = Image.builder()
                .id(id)
                .originFileName(originFileName)
                .serverFileName(serverFileName)
                .storedFilePath(storedFilePath)
                .build();
        return build;
    }

    @Builder
    public ImageDto(Long id, String originFileName, String serverFileName, String storedFilePath) {
        this.id = id;
        this.originFileName = originFileName;
        this.serverFileName = serverFileName;
        this.storedFilePath = storedFilePath;
    }
}