package personal.delivery.menu.service;

import personal.delivery.menu.dto.MenuChangeDto;
import personal.delivery.menu.dto.MenuDto;
import personal.delivery.menu.dto.MenuResponseDto;

import java.util.List;

public interface MenuService {

    MenuResponseDto saveMenu(MenuDto menuDto);

    MenuResponseDto getMenu(Long id);

    List<MenuResponseDto> getAllMenu();

    MenuResponseDto changeMenu(MenuChangeDto menuChangeDto) throws Exception;

    MenuResponseDto deleteMenu(Long id) throws Exception;

}
