package personal.delivery.menu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.config.BeanConfiguration;
import personal.delivery.menu.Menu;
import personal.delivery.menu.dto.MenuDto;
import personal.delivery.menu.dto.MenuResponseDto;
import personal.delivery.menu.repository.JpaMenuRepository;

import java.time.LocalDateTime;
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
                .regTime(LocalDateTime.now())
                .build();

        Menu savedMenu = jpaMenuRepository.insertMenu(menu);

        MenuResponseDto menuResponseDto = beanConfiguration.modelMapper()
                .map(savedMenu, MenuResponseDto.class);

        return menuResponseDto;

    }

    @Override
    public MenuResponseDto getMenu(Long id) {

        Menu menu = jpaMenuRepository.selectMenu(id);

        MenuResponseDto menuResponseDto = beanConfiguration.modelMapper()
                .map(menu, MenuResponseDto.class);

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

    @Transactional
    @Override
    public MenuResponseDto changeMenu(Long id, MenuDto menuDto) throws Exception {

        Menu menu = Menu.builder()
                .id(id)
                .name(menuDto.getName())
                .price(menuDto.getPrice())
                .salesRate(menuDto.getSalesRate())
                .flavor(menuDto.getFlavor())
                .portions(menuDto.getPortions())
                .cookingTime(menuDto.getCookingTime())
                .menuType(menuDto.getMenuType())
                .foodType(menuDto.getFoodType())
                .build();

        int presentStock = getMenu(id).getStock();

        menu.importPresentStock(presentStock);

        int stock = menuDto.getStock();

        menu.addStock(stock);

        Menu changedMenu = jpaMenuRepository.updateMenu(menu);

        MenuResponseDto menuResponseDto = beanConfiguration.modelMapper()
                .map(changedMenu, MenuResponseDto.class);

        return menuResponseDto;

    }

    @Override
    public MenuResponseDto deleteMenu(Long id) throws Exception {
        Menu deletedMenu = jpaMenuRepository.deleteMenu(id);

        MenuResponseDto menuResponseDto = beanConfiguration.modelMapper()
                .map(deletedMenu, MenuResponseDto.class);

        return menuResponseDto;
    }

}