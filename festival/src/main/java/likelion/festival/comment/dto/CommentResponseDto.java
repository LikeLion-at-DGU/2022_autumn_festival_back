package likelion.festival.comment.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;

    private String writer;

    private String content;

    private LocalDateTime createdDateTime;

}
