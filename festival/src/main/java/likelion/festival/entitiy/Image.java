package likelion.festival.entitiy;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Image {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String origin_file_name;

    private String server_file_name;

    private String stored_file_path;

    private long file_size;


    @Builder
    public Image(Long id, String origin_file_name, String server_file_name, String stored_file_path) {
        this.id = id;
        this.origin_file_name = origin_file_name;
        this.server_file_name = server_file_name;
        this.stored_file_path = stored_file_path;
    }
}
