package likelion.festival.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Booth {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String content;

    private String notice;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BoothType boothType;

    @NotNull
    private String introduction;

    @NotNull
    private String location;

    private Integer boothNo;

    @NotNull
    private String startAt;

    @NotNull
    private String endAt;

    @OneToMany(mappedBy = "booth")
    private List<Menu> menus = new ArrayList<>();

    @OneToMany(mappedBy = "booth")
    private List<Likes> likes = new ArrayList<>();


    @JsonManagedReference
    @OneToMany(mappedBy = "booth")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "booth",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Image> images = new ArrayList<>();
}
