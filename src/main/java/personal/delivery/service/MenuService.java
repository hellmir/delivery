package personal.delivery.service;

import personal.delivery.dto.MenuDto;
import personal.delivery.dto.MenuResponseDto;

public interface MenuService {

    MenuResponseDto getMenu(Long id);

    MenuResponseDto saveMenu(MenuDto menuDto);

}
