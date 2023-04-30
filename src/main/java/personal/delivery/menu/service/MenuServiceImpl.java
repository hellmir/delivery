package personal.delivery.menu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import personal.delivery.menu.Menu;
import personal.delivery.menu.dto.MenuChangeDto;
import personal.delivery.menu.dto.MenuDto;
import personal.delivery.menu.dto.MenuResponseDto;
import personal.delivery.menu.repository.JpaMenuRepository;
import personal.delivery.config.BeanConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final JpaMenuRepository jpaMenuRepository;

    @Override
    public MenuResponseDto saveMenu(MenuDto menuDto) {

        Menu menu = Menu.builder()
                .name(menuDto.getName())
                .price(menuDto.getPrice())
                .salesRate(menuDto.getSalesRate())
                .stock(menuDto.getStock())
                .flavor(menuDto.getFlavor())
                .portions(menuDto.getPortions())
                .cookingTime(menuDto.getCookingTime())
                .menuType(menuDto.getMenuType())
                .foodType(menuDto.getFoodType())
                .popularMenu(menuDto.isPopularMenu())
                .build();

        jpaMenuRepository.insertMenu(menu);

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

    @Override
    public MenuResponseDto getMenu(Long id) {
        Menu menu = jpaMenuRepository.selectMenu(id);
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

    @Override
    public List<MenuResponseDto> getAllMenu() {

        List<Menu> menuList = jpaMenuRepository.selectAllMenu();

            List<MenuResponseDto> menuResponseDtoList = menuList.stream()
                    .map(menu -> BeanConfiguration.modelMapper().map(menu, MenuResponseDto.class))
                    .collect(Collectors.toList());

        return menuResponseDtoList;

    }

    @Override
    public MenuResponseDto changeMenu(MenuChangeDto menuChangeDto) throws Exception {

        Menu menu = Menu.builder()
                .id(menuChangeDto.getId())
                        .build();

        menu.changeName(menuChangeDto.getName());
        menu.changePrice(menuChangeDto.getPrice());
        menu.changeSalesRate(menuChangeDto.getSalesRate());

        int presentStock = getMenu(menuChangeDto.getId()).getStock();

        menu.importPresentStock(presentStock);

        menu.addStock(menuChangeDto.getStock());

        menu.changeFlavor(menuChangeDto.getFlavor());
        menu.changePortions(menuChangeDto.getPortions());
        menu.changeCookingTime(menuChangeDto.getCookingTime());
        menu.changeMenuType(menuChangeDto.getMenuType());
        menu.changeFoodType(menuChangeDto.getFoodType());

        Menu changedMenu = jpaMenuRepository.updateMenu(menu);

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

    @Override
    public MenuResponseDto deleteMenu(Long id) throws Exception {
        Menu deletedMenu = jpaMenuRepository.deleteMenu(id);

        MenuResponseDto menuResponseDto = new MenuResponseDto();
        menuResponseDto.setId(deletedMenu.getId());
        menuResponseDto.setName(deletedMenu.getName());
        menuResponseDto.setPrice(deletedMenu.getPrice());
        menuResponseDto.setSalesRate(deletedMenu.getSalesRate());
        menuResponseDto.setStock(deletedMenu.getStock());
        menuResponseDto.setFlavor(deletedMenu.getFlavor());
        menuResponseDto.setPortions(deletedMenu.getPortions());
        menuResponseDto.setCookingTime(deletedMenu.getCookingTime());
        menuResponseDto.setMenuType(deletedMenu.getMenuType());
        menuResponseDto.setFoodType(deletedMenu.getFoodType());
        menuResponseDto.setPopularMenu(deletedMenu.getPopularMenu());

        return menuResponseDto;
    }

}