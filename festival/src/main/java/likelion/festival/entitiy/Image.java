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

    private String originFileName;

    private String serverFileName;

    private String storedFilePath;

    @Builder
    public Image(Long id, String originFileName, String serverFileName, String storedFilePath) {
        this.id = id;
        this.originFileName = originFileName;
        this.serverFileName = serverFileName;
        this.storedFilePath = storedFilePath;
    }
}
