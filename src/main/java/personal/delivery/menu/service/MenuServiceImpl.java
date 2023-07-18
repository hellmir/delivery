package personal.delivery.menu.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.config.BeanConfiguration;
import personal.delivery.exception.OutOfStockException;
import personal.delivery.menu.Menu;
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

        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new EntityNotFoundException("해당 가게를 찾을 수 없습니다. (shopId: " + shopId + ")"));

        Menu menu = Menu.builder()
                .shop(shop)
                .name(menuDto.getName())
                .price(menuDto.getPrice())
                .salesRate(0)
                .stock(menuDto.getStock())
                .flavor(menuDto.getFlavor())
                .portions(menuDto.getPortions())
                .cookingTime(menuDto.getCookingTime())
                .menuType(menuDto.getMenuType())
                .foodType(menuDto.getFoodType())
                .menuOptions(menuDto.getMenuOptions())
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
                .orElseThrow(() -> new EntityNotFoundException("해당 메뉴를 찾을 수 없습니다. (menuId: " + id + ")"));

        int modifiedStock = menu.getStock() + menuDto.getStock();

        if (modifiedStock < 0) {
            throw new OutOfStockException("재료 최소 수량: 0 (현재 재고: " + menu.getStock() + ")");
        }

        Menu changingMenu = Menu.builder()
                .id(id)
                .shop(menu.getShop())
                .name(menuDto.getName() != null ? menuDto.getName() : menu.getName())
                .price(menuDto.getPrice() > 0 ? menuDto.getPrice() : menu.getPrice())
                .salesRate(menuDto.getSalesRate() == -1 ? 0 : menu.getSalesRate())
                .stock(modifiedStock)
                .flavor(menuDto.getFlavor() != null ? menuDto.getFlavor() : menu.getFlavor())
                .portions(menuDto.getPortions() > 0 ? menuDto.getPortions() : menu.getPortions())
                .cookingTime(menuDto.getCookingTime() > 0 ? menuDto.getCookingTime() : menu.getCookingTime())
                .menuType(menuDto.getMenuType() != null ? menuDto.getMenuType() : menu.getMenuType())
                .foodType(menuDto.getFoodType() != null ? menuDto.getFoodType() : menu.getFoodType())
                .menuOptions(menuDto.getMenuOptions() != null ? menuDto.getMenuOptions() : menu.getMenuOptions())
                .registrationTime(menu.getRegistrationTime())
                .updateTime(LocalDateTime.now())
                .build();

        Menu changedMenu = menuRepository.save(changingMenu);

        MenuResponseDto menuResponseDto = beanConfiguration.modelMapper()
                .map(changedMenu, MenuResponseDto.class);

        return menuResponseDto;

    }

    public MenuResponseDto deleteMenu(Long id) throws Exception {

        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 메뉴를 찾을 수 없습니다. (menuId: " + id + ")"));

        Menu deletedMenu;

        menuRepository.delete(menu);
        deletedMenu = menu;

        MenuResponseDto menuResponseDto = beanConfiguration.modelMapper()
                .map(deletedMenu, MenuResponseDto.class);

        return menuResponseDto;

    }

}