package likelion.festival.dto;

import likelion.festival.entitiy.Image;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ImageDto {

    private Long id;

    private String origin_file_name;

    private String server_file_name;

    private String stored_file_path;


    public Image toEntity(){
        Image build = Image.builder()
                .id(id)
                .origin_file_name(origin_file_name)
                .server_file_name(server_file_name)
                .stored_file_path(stored_file_path)
                .build();
        return build;
    }

    @Builder
    public ImageDto(Long id, String origin_file_name, String server_file_name, String stored_file_path) {
        this.id = id;
        this.origin_file_name = origin_file_name;
        this.server_file_name = server_file_name;
        this.stored_file_path = stored_file_path;
    }
}
