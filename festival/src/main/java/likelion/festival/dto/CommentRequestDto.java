package likelion.festival.dto;


import likelion.festival.entity.Booth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {
    private String writer;

    private String password;

    private String ip;

    private Boolean active;

    private String content;

    private Booth booth;
}
