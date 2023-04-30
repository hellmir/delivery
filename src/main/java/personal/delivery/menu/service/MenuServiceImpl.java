package personal.delivery.menu.service;

import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.config.BeanConfiguration;
import personal.delivery.menu.Menu;
import personal.delivery.menu.dto.MenuChangeDto;
import personal.delivery.menu.dto.MenuDto;
import personal.delivery.menu.dto.MenuResponseDto;
import personal.delivery.menu.repository.JpaMenuRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    private final JpaMenuRepository jpaMenuRepository;
    private final BeanConfiguration beanConfiguration;

    @Autowired
    public MenuServiceImpl(JpaMenuRepository jpaMenuRepository, BeanConfiguration beanConfiguration) {
        this.jpaMenuRepository = jpaMenuRepository;
        this.beanConfiguration = beanConfiguration;
    }

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

        Menu savedMenu = jpaMenuRepository.insertMenu(menu);

        MenuResponseDto menuResponseDto = beanConfiguration.modelMapper()
                .map(savedMenu,MenuResponseDto.class);

        return menuResponseDto;

    }

    @Override
    public MenuResponseDto getMenu(Long id) {

        Menu menu = jpaMenuRepository.selectMenu(id);

        MenuResponseDto menuResponseDto = beanConfiguration.modelMapper()
                .map(menu,MenuResponseDto.class);

        return menuResponseDto;

    }

    @Override
    public List<MenuResponseDto> getAllMenu() {

        List<Menu> menuList = jpaMenuRepository.selectAllMenu();

        List<MenuResponseDto> menuResponseDtoList = menuList.stream()
                .map(menu -> beanConfiguration.modelMapper().map(menu, MenuResponseDto.class))
                .collect(Collectors.toList());

        return menuResponseDtoList;

    }

    @Override
    public MenuResponseDto changeMenu(MenuChangeDto menuChangeDto) throws Exception {

        Menu menu = Menu.builder()
                .id(menuChangeDto.getId())
                .build();

        @NotBlank
        String name = menuChangeDto.getName();

        @Positive
        int price = menuChangeDto.getPrice();

        @Positive
        @Negative
        int salesRate = menuChangeDto.getSalesRate();

        @NotBlank
        String flavor = menuChangeDto.getFlavor();

        @Positive
        int portions = menuChangeDto.getPortions();

        @Positive
        int cookingTime = menuChangeDto.getCookingTime();

        @NotBlank
        String menuType = menuChangeDto.getMenuType();

        @NotBlank
        String foodType = menuChangeDto.getFoodType();

        menu.updateMenu(name, price, salesRate, flavor, portions, cookingTime, menuType, foodType);

        int presentStock = getMenu(menuChangeDto.getId()).getStock();

        menu.importPresentStock(presentStock);

        @Positive
        @Negative
        int stock = menuChangeDto.getStock();

        menu.addStock(stock);

        Menu changedMenu = jpaMenuRepository.updateMenu(menu);

        MenuResponseDto menuResponseDto = beanConfiguration.modelMapper()
                .map(changedMenu,MenuResponseDto.class);

        return menuResponseDto;

    }

    @Override
    public MenuResponseDto deleteMenu(Long id) throws Exception {
        Menu deletedMenu = jpaMenuRepository.deleteMenu(id);

        MenuResponseDto menuResponseDto = beanConfiguration.modelMapper()
                .map(deletedMenu,MenuResponseDto.class);

        return menuResponseDto;
    }

}