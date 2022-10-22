package likelion.festival.comment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import likelion.festival.booth.entity.Booth;
import likelion.festival.global.baseEntity.BaseEntity;
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

    @NotNull
    private String writer;

    @NotNull
    private String password;

    @NotNull
    private String content;

    @NotNull
    private String ip;

    @NotNull
    private Boolean active;

    @NotNull
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booth_id")
    private Booth booth;

    public void setIp(String ip){
        this.ip = ip;
    }

    public void setActivte(Boolean active){
        this.active = active;
    }
}
