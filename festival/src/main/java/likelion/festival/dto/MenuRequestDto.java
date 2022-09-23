package likelion.festival.dto;

import likelion.festival.entitiy.Booth;
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
