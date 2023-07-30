package personal.delivery.menu.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.configuration.BeanConfiguration;
import personal.delivery.exception.OutOfStockException;
import personal.delivery.menu.Menu;
import personal.delivery.menu.dto.MenuRequestDto;
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
    public MenuResponseDto saveMenu(Long shopId, MenuRequestDto menuRequestDto) {

        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new EntityNotFoundException("해당 가게를 찾을 수 없습니다. (shopId: " + shopId + ")"));

        Menu menu = Menu.builder()
                .shop(shop)
                .name(menuRequestDto.getName())
                .price(menuRequestDto.getPrice())
                .salesRate(0)
                .stock(menuRequestDto.getStock())
                .flavor(menuRequestDto.getFlavor())
                .portions(menuRequestDto.getPortions())
                .cookingTime(menuRequestDto.getCookingTime())
                .menuType(menuRequestDto.getMenuType())
                .foodType(menuRequestDto.getFoodType())
                .menuOptions(menuRequestDto.getMenuOptions())
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
    public MenuResponseDto changeMenu(Long id, MenuRequestDto menuRequestDto) {

        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 메뉴를 찾을 수 없습니다. (menuId: " + id + ")"));

        int modifiedStock = menu.getStock() + menuRequestDto.getStock();

        if (modifiedStock < 0) {
            throw new OutOfStockException("재료 최소 수량: 0 (현재 재고: " + menu.getStock() + ")");
        }

        Menu changingMenu = Menu.builder()
                .id(id)
                .shop(menu.getShop())
                .name(menuRequestDto.getName() != null ? menuRequestDto.getName() : menu.getName())
                .price(menuRequestDto.getPrice() > 0 ? menuRequestDto.getPrice() : menu.getPrice())
                .salesRate(menuRequestDto.getSalesRate() == -1 ? 0 : menu.getSalesRate())
                .stock(modifiedStock)
                .flavor(menuRequestDto.getFlavor() != null ? menuRequestDto.getFlavor() : menu.getFlavor())
                .portions(menuRequestDto.getPortions() > 0 ? menuRequestDto.getPortions() : menu.getPortions())
                .cookingTime(menuRequestDto.getCookingTime() > 0
                        ? menuRequestDto.getCookingTime() : menu.getCookingTime())
                .menuType(menuRequestDto.getMenuType() != null ? menuRequestDto.getMenuType() : menu.getMenuType())
                .foodType(menuRequestDto.getFoodType() != null ? menuRequestDto.getFoodType() : menu.getFoodType())
                .menuOptions(menuRequestDto.getMenuOptions() != null
                        ? menuRequestDto.getMenuOptions() : menu.getMenuOptions())
                .registrationTime((menu.getRegisteredTime()))
                .updateTime(LocalDateTime.now())
                .build();

        Menu changedMenu = menuRepository.save(changingMenu);

        MenuResponseDto menuResponseDto = beanConfiguration.modelMapper()
                .map(changedMenu, MenuResponseDto.class);

        menuResponseDto.setRegisteredTime(menu.getRegistrationTime());
        menuResponseDto.setUpdatedTime(changedMenu.getUpdateTime());

        return menuResponseDto;

    }

    public void deleteMenu(Long id) {

        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 메뉴를 찾을 수 없습니다. (menuId: " + id + ")"));

        menuRepository.delete(menu);

    }

}