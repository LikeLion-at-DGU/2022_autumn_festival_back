package likelion.festival.dto;


import likelion.festival.entitiy.Booth;
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

    private String content;

    private Booth booth;
}
