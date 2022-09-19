package likelion.festival.entitiy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
@Builder
@Entity
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String writer;

    private String password;

    private String content;

    // Booth
//    @ManyToOne(targetEntity = Booth.class, fetch = FetchType.LAZY)
//    @JoinColumn(name = "booth_id")
//    private Booth booth;

}
