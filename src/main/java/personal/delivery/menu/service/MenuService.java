package personal.delivery.menu.service;

import personal.delivery.menu.dto.MenuRequestDto;
import personal.delivery.menu.dto.MenuResponseDto;

import java.util.List;

public interface MenuService {

    MenuResponseDto saveMenu(Long shopId, MenuRequestDto menuRequestDto);

    List<MenuResponseDto> getAllMenus();

    List<MenuResponseDto> getAllShopMenus(Long shopId);

    MenuResponseDto getMenu(Long id);

    MenuResponseDto changeMenu(Long id, MenuRequestDto menuRequestDto);

    void deleteMenu(Long id);

}
