package likelion.festival.menu.dto;

import likelion.festival.booth.entity.Booth;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuRequestDto {
    private Long id;

    private String name;

    private Long price;

    private Booth booth;
}
