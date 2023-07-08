package personal.delivery.menu.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.config.BeanConfiguration;
import personal.delivery.menu.Menu;
import personal.delivery.menu.Menu.MenuBuilder;
import personal.delivery.menu.dto.MenuDto;
import personal.delivery.menu.dto.MenuResponseDto;
import personal.delivery.menu.repository.MenuRepository;
import personal.delivery.shop.entity.Shop;
import personal.delivery.shop.repository.ShopRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final ShopRepository shopRepository;
    private final BeanConfiguration beanConfiguration;

    @Override
    public MenuResponseDto saveMenu(Long shopId, MenuDto menuDto) {

        Shop shop = shopRepository.getById(shopId);

        Menu menu = Menu.builder()
                .shop(shop)
                .name(menuDto.getName())
                .price(menuDto.getPrice())
                .salesRate(menuDto.getSalesRate())
                .stock(menuDto.getStock())
                .flavor(menuDto.getFlavor())
                .portions(menuDto.getPortions())
                .cookingTime(menuDto.getCookingTime())
                .menuType(menuDto.getMenuType())
                .foodType(menuDto.getFoodType())
                .menuOption(menuDto.getMenuOption())
                .registrationTime(LocalDateTime.now())
                .build();

        Menu savedMenu = menuRepository.save(menu);

        MenuResponseDto menuResponseDto = beanConfiguration.modelMapper()
                .map(savedMenu, MenuResponseDto.class);

        return menuResponseDto;

    }

    @Override
    public List<MenuResponseDto> getAllMenu() {

        List<Menu> menuList = menuRepository.findAll();

        List<MenuResponseDto> menuResponseDtoList = menuList.stream()
                .map(menu -> beanConfiguration.modelMapper().map(menu, MenuResponseDto.class))
                .collect(Collectors.toList());

        return menuResponseDtoList;

    }

    @Override
    public List<MenuResponseDto> getAllShopMenu(Long shopId) {

        List<Menu> menuList = menuRepository.findAllByShop_Id(shopId);

        List<MenuResponseDto> menuResponseDtoList = menuList.stream()
                .map(menu -> beanConfiguration.modelMapper().map(menu, MenuResponseDto.class))
                .collect(Collectors.toList());

        return menuResponseDtoList;

    }

    @Override
    public MenuResponseDto getMenu(Long id) {

        Menu selectedMenu = menuRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        MenuResponseDto menuResponseDto = beanConfiguration.modelMapper()
                .map(selectedMenu, MenuResponseDto.class);

        return menuResponseDto;

    }

    @Override
    public MenuResponseDto changeMenu(Long id, MenuDto menuDto) throws Exception {

        Menu menu = menuRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        MenuBuilder menuBuilder = Menu.builder();

        menuBuilder.id(id);

        if (menuDto.getName() != null) {
            menuBuilder.name(menuDto.getName());
        } else {
            menuBuilder.name(menu.getName());
        }

        if (menuDto.getPrice() > 0) {
            menuBuilder.price(menuDto.getPrice());
        } else {
            menuBuilder.price(menu.getPrice());
        }

        if (menuDto.getSalesRate() == -1) {
            menuBuilder.salesRate(0);
        } else {
            menuBuilder.salesRate(menu.getSalesRate());
        }

        menuBuilder.stock(menu.getStock() + menuDto.getStock());


        if (menuDto.getFlavor() != null) {
            menuBuilder.flavor(menuDto.getFlavor());
        } else {
            menuBuilder.flavor(menu.getFlavor());
        }

        if (menuDto.getPortions() > 0) {
            menuBuilder.portions(menuDto.getPortions());
        } else {
            menuBuilder.portions(menu.getPortions());
        }

        if (menuDto.getCookingTime() > 0) {
            menuBuilder.cookingTime(menuDto.getCookingTime());
        } else {
            menuBuilder.cookingTime(menu.getCookingTime());
        }

        if (menuDto.getMenuType() != null) {
            menuBuilder.menuType(menuDto.getMenuType());
        } else {
            menuBuilder.menuType(menu.getMenuType());
        }

        if (menuDto.getFoodType() != null) {
            menuBuilder.foodType(menuDto.getFoodType());
        } else {
            menuBuilder.foodType(menu.getFoodType());
        }

        if (menuDto.getMenuOption() != null) {
            menuBuilder.menuOption(menuDto.getMenuOption());
        } else {
            menuBuilder.menuOption(menu.getMenuOption());
        }

        menuBuilder.registrationTime(menu.getRegistrationTime());

        menuBuilder.updateTime(LocalDateTime.now());

        Menu changedMenu = menuBuilder.build();

        menuRepository.save(changedMenu);

        MenuResponseDto menuResponseDto = beanConfiguration.modelMapper()
                .map(changedMenu, MenuResponseDto.class);

        return menuResponseDto;

    }

    public MenuResponseDto deleteMenu(Long id) throws Exception {

        Menu menu = menuRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        Menu deletedMenu;

        menuRepository.delete(menu);
        deletedMenu = menu;

        MenuResponseDto menuResponseDto = beanConfiguration.modelMapper()
                .map(deletedMenu, MenuResponseDto.class);

        return menuResponseDto;

    }

}