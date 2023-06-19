package personal.delivery.menu.service;

import personal.delivery.menu.dto.MenuDto;
import personal.delivery.menu.dto.MenuResponseDto;

import java.util.List;

public interface MenuService {

    MenuResponseDto saveMenu(MenuDto menuDto);

    List<MenuResponseDto> getAllMenu();

    MenuResponseDto getMenu(Long id);

    MenuResponseDto changeMenu(Long id, MenuDto menuDto) throws Exception;

    MenuResponseDto deleteMenu(Long id) throws Exception;

}
