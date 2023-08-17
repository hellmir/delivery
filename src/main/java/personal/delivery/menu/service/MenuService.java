package personal.delivery.menu.service;

import personal.delivery.menu.dto.MenuRequestDto;
import personal.delivery.menu.dto.MenuResponseDto;

import java.util.List;

public interface MenuService {

    MenuResponseDto saveMenu(Long shopId, MenuRequestDto menuRequestDto);

    List<MenuResponseDto> getAllMenus();

    MenuResponseDto getMenu(Long shopId, Long id);

    MenuResponseDto changeMenu(Long shopId, Long id, MenuRequestDto menuRequestDto);

    void deleteMenu(Long shopId, Long id);

    void deleteSeveralMenus(Long shopId, List<Long> ids);

}
