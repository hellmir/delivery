package personal.delivery.service;

import personal.delivery.dto.MenuChangeDto;
import personal.delivery.dto.MenuDto;
import personal.delivery.dto.MenuResponseDto;

import java.util.List;

public interface MenuService {

    MenuResponseDto saveMenu(MenuDto menuDto);

    MenuResponseDto getMenu(Long id);

    List<MenuResponseDto> getAllMenu() throws Exception;

    MenuResponseDto changeMenu(MenuChangeDto menuChangeDto) throws Exception;

    String deleteMenu(Long id) throws Exception;

}
