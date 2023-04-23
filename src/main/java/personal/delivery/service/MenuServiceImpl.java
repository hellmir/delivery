package personal.delivery.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import personal.delivery.dao.MenuDAO;
import personal.delivery.dao.MenuDAOImpl;
import personal.delivery.domain.Menu;
import personal.delivery.dto.MenuChangeDto;
import personal.delivery.dto.MenuDto;
import personal.delivery.dto.MenuResponseDto;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements personal.delivery.service.MenuService {

    private final MenuDAO menuDAO;

    // 1. MenuController로부터 MenuDto를 통해 신메뉴 요청값 받음
    // 2. MenuDAO를 통해 Menu 엔티티에 등록 (DTO에서 Entity로 변환)
    // 3. MenuResponseDto값을 갱신해 응답값으로 return (Entity에서 DTO로 변환)
    @Override
    public MenuResponseDto saveMenu(MenuDto menuDto) {
        Menu menu = new Menu();
        menu.setName(menuDto.getName());
        menu.setPrice(menuDto.getPrice());
        menu.setSalesRate(menuDto.getSalesRate());
        menu.setStock(menuDto.getStock());
        menu.setFlavor(menuDto.getFlavor());
        menu.setPortions(menuDto.getPortions());
        menu.setCookingTime(menuDto.getCookingTime());
        menu.setMenuType(menuDto.getMenuType());
        menu.setFoodType(menuDto.getFoodType());
        menu.setPopularMenu(menuDto.isPopularMenu());

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

    // 1. MenuController로부터 요청 받음
    // 2. MenuDAO를 통해 해당 Menu 엔티티의 데이터 목록을 받음
    // 3. 목록 크기만큼 getMenu method를 호출
    // 4. MenuResponseDtoList값을 갱신해 응답값으로 return (Entity에서 DTO로 변환)
    @Override
    public List<MenuResponseDto> getAllMenu() throws Exception {
        List<Menu> menu = menuDAO.selectAllMenu();
        List<MenuResponseDto> menuResponseDtoList = new ArrayList<>();

        for (int i = 1; i <= menu.size(); i++) {
            menuResponseDtoList.add( getMenu( (long)i ) );
        }

        return menuResponseDtoList;
    }

    // 1. MenuController로부터 MenuDto를 통해 메뉴 수정 요청값 받음
    // 2. MenuChangeDAO를 통해 Menu 엔티티에 등록 (DTO에서 Entity로 변환)
    // 3. MenuResponseDto값을 갱신해 응답값으로 return (Entity에서 DTO로 변환)
    @Override
    public MenuResponseDto changeMenu(MenuChangeDto menuChangeDto) throws Exception {

        Menu menu = new Menu();
        menu.setId(menuChangeDto.getId());
        menu.setName(menuChangeDto.getName());
        menu.setPrice(menuChangeDto.getPrice());
        menu.setSalesRate(menuChangeDto.getSalesRate());
        menu.setStock(menuChangeDto.getStock());
        menu.setFlavor(menuChangeDto.getFlavor());
        menu.setPortions(menuChangeDto.getPortions());
        menu.setCookingTime(menuChangeDto.getCookingTime());
        menu.setMenuType(menuChangeDto.getMenuType());
        menu.setFoodType(menuChangeDto.getFoodType());

        Menu changedMenu = menuDAO.updateMenu(menu);

        MenuResponseDto menuResponseDto = new MenuResponseDto();
        menuResponseDto.setId(changedMenu.getId());
        menuResponseDto.setName(changedMenu.getName());
        menuResponseDto.setPrice(changedMenu.getPrice());
        menuResponseDto.setSalesRate(changedMenu.getSalesRate());
        menuResponseDto.setStock(changedMenu.getStock());
        menuResponseDto.setFlavor(changedMenu.getFlavor());
        menuResponseDto.setPortions(changedMenu.getPortions());
        menuResponseDto.setCookingTime(changedMenu.getCookingTime());
        menuResponseDto.setMenuType(changedMenu.getMenuType());
        menuResponseDto.setFoodType(changedMenu.getFoodType());
        menuResponseDto.setPopularMenu(changedMenu.getPopularMenu());

        return menuResponseDto;

    }

    // 1. MenuController로부터 ID 요청값 받음
    // 2. MenuDAO를 통해 메뉴 삭제
    // 3. 메뉴 name을 응답값으로 return
    @Override
    public String deleteMenu(Long id) throws Exception {
        String name = menuDAO.deleteMenu(id);

        return name;
    }

}