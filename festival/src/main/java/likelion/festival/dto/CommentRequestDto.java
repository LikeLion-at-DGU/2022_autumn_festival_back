package likelion.festival.dto;

import likelion.festival.entitiy.Booth;
import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {

    private String writer;

    private String password;

    private String content;

    private Booth booth;
}
