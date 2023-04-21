package personal.delivery.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import personal.delivery.dao.MenuDAO;
import personal.delivery.domain.Menu;
import personal.delivery.dto.MenuDto;
import personal.delivery.dto.MenuResponseDto;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuDAO menuDAO;

    // 1. MenuController로부터 ID 요청값 받음
    // 2. MenuDAO를 통해 해당 Menu 엔티티의 데이터를 받음
    // 3. MenuResponseDto값을 갱신해 응답값으로 return (Entity에서 DTO로 변환)
    @Override
    public MenuResponseDto getMenu(Long id) {
        Menu menu = menuDAO.selectMenu(id);
        MenuResponseDto menuResponseDto = new MenuResponseDto();
        menuResponseDto.setId(menu.getId());
        menuResponseDto.setName(menu.getName());
        menuResponseDto.setPrice(menu.getPrice());
        menuResponseDto.setSalesRate(menu.getSalesRate());
        menuResponseDto.setStock(menu.getStock());
        menuResponseDto.setFlavor(menu.getFlavor());
        menuResponseDto.setPortions(menu.getPortions());
        menuResponseDto.setCookingTime(menu.getCookingTime());
        menuResponseDto.setMenuType(menu.getMenuType());
        menuResponseDto.setFoodType(menu.getFoodType());
        menuResponseDto.setPopularMenu(menu.getPopularMenu());

        return menuResponseDto;
    }

    // 1. Controller로부터 MenuDto를 통해 신메뉴 요청값 받음
    // 2. MenuDAO를 통해 menu 엔티티에 등록 (DTO에서 Entity로 변환)
    // 3. MenuResponseDto값을 갱신해 응답값으로 return (Entity에서 DTO로 변환)
    @Override
    public MenuResponseDto saveMenu(MenuDto menuDto) {
        Menu menu = new Menu();
        menu.setId(menu.getId());
        menu.setName(menu.getName());
        menu.setPrice(menu.getPrice());
        menu.setSalesRate(menu.getSalesRate());
        menu.setStock(menu.getStock());
        menu.setFlavor(menu.getFlavor());
        menu.setPortions(menu.getPortions());
        menu.setCookingTime(menu.getCookingTime());
        menu.setMenuType(menu.getMenuType());
        menu.setFoodType(menu.getFoodType());
        menu.setPopularMenu(menu.getPopularMenu());

        Menu savedMenu = menuDAO.insertMenu(menu);

        MenuResponseDto menuResponseDto = new MenuResponseDto();
        menuResponseDto.setId(menu.getId());
        menuResponseDto.setName(menu.getName());
        menuResponseDto.setPrice(menu.getPrice());
        menuResponseDto.setSalesRate(menu.getSalesRate());
        menuResponseDto.setStock(menu.getStock());
        menuResponseDto.setFlavor(menu.getFlavor());
        menuResponseDto.setPortions(menu.getPortions());
        menuResponseDto.setCookingTime(menu.getCookingTime());
        menuResponseDto.setMenuType(menu.getMenuType());
        menuResponseDto.setFoodType(menu.getFoodType());
        menuResponseDto.setPopularMenu(menu.getPopularMenu());

        return menuResponseDto;

    }
}