package likelion.festival.dto;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikesResponseDto {
    private Long boothId;

    private String cookieKey;
}
