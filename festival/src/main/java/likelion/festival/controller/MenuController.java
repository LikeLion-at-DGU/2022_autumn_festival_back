package likelion.festival.controller;

import likelion.festival.dto.*;
import likelion.festival.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("${app-menu}")
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
