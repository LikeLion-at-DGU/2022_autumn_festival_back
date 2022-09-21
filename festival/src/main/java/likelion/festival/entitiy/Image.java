package likelion.festival.entitiy;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Image {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String origin_file_name;

    private String stored_file_path;

    private long file_size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booth_id")
    private Booth booth;
}
