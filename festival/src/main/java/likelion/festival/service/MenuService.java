package likelion.festival.service;

import likelion.festival.dto.MenuRequestDto;
import likelion.festival.dto.MenuResponseDto;
import likelion.festival.entity.Booth;
import likelion.festival.entity.Menu;
import likelion.festival.exception.WrongBoothId;
import likelion.festival.repository.BoothRepository;
import likelion.festival.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final BoothRepository boothRepository;

    @Transactional
    public MenuResponseDto create(Long boothId, MenuRequestDto menuRequestDto){
        Optional<Booth> booth = boothRepository.findById(boothId);

        if(booth.isEmpty()){
            throw new WrongBoothId();
        }
        menuRequestDto.setBooth(booth.get());
        Menu newMenu = dtoToEntity(menuRequestDto);
        Menu menu = menuRepository.save(newMenu);
        return entityToDto(menu);
    }

    public List<MenuResponseDto> getAll(Long boothId){
        Optional<Booth> booth = boothRepository.findById(boothId);
        if (booth.isEmpty()){
            throw new WrongBoothId();
        }
        List<Menu> menus = booth.get().getMenus();
        return getDtoList(menus);
    }

    @Transactional
    public MenuResponseDto update(Long id, MenuRequestDto menuRequestDto){
        Optional<Menu> menu = menuRepository.findById(id);
        if (menu.isEmpty()){
            return null; // TODO: likes exception 손 볼 때 같이 할 예정
        }
        menuRequestDto.setId(menu.get().getId());
        Menu updateMenu = dtoToEntity(menuRequestDto);
        menuRepository.save(updateMenu);
        return entityToDto(updateMenu);
    }

    @Transactional
    public void delete(Long id){
        Optional<Menu> menu = menuRepository.findById(id);
        if (menu.isEmpty()){
            return; // TODO: likes exception 손 볼 때 같이 할 예정
        }
        menuRepository.delete(menu.get());
    }

    private Menu dtoToEntity(MenuRequestDto menuRequestDto) {
        return Menu.builder()
                .name(menuRequestDto.getName())
                .price(menuRequestDto.getPrice())
                .booth(menuRequestDto.getBooth())
                .build();
    }

    private MenuResponseDto entityToDto(Menu menu){
        return MenuResponseDto.builder()
                .id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .build();
    }

    private List<MenuResponseDto> getDtoList(List<Menu> menus){
        return menus.stream().map(this::entityToDto)
                .collect(Collectors.toList());
    }
}
