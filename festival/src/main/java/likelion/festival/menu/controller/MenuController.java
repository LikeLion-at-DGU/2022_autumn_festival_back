package likelion.festival.menu.controller;

import likelion.festival.menu.dto.MenuRequestDto;
import likelion.festival.menu.dto.MenuResponseDto;
import likelion.festival.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("api/menu")
@RestController
public class MenuController {

    private final MenuService menuService;

    @PutMapping("/{id}")
    public MenuResponseDto updateMenu(@PathVariable Long id, @RequestBody MenuRequestDto menuRequestDto){
        return menuService.update(id, menuRequestDto);
    }

    @DeleteMapping("{id}")
    public String deleteMenu(@PathVariable Long id){
        return menuService.delete(id);
    }
}
