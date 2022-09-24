package likelion.festival.dto;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuResponseDto {
    private Long id;

    private String name;

    private Long price;
}
