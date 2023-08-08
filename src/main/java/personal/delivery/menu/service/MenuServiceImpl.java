package personal.delivery.menu.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    private final ShopRepository shopRepository;
    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;

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

        return modelMapper.map(savedMenu, MenuResponseDto.class);

    }

    @Override
    public List<MenuResponseDto> getAllMenus() {

        List<Menu> menuList = menuRepository.findAll();

        List<MenuResponseDto> menuResponseDtoList = menuList.stream()
                .map(menu -> modelMapper.map(menu, MenuResponseDto.class))
                .collect(Collectors.toList());

        return menuResponseDtoList;

    }

    @Override
    public List<MenuResponseDto> getAllShopMenus(Long shopId) {

        List<Menu> menuList = menuRepository.findAllByShop_Id(shopId);

        List<MenuResponseDto> menuResponseDtoList = menuList.stream()
                .map(menu -> modelMapper.map(menu, MenuResponseDto.class))
                .collect(Collectors.toList());

        return menuResponseDtoList;

    }

    @Override
    public MenuResponseDto getMenu(Long id) {

        Menu selectedMenu = menuRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return modelMapper.map(selectedMenu, MenuResponseDto.class);

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
                .registeredTime(menu.getRegisteredTime())
                .updatedTime(LocalDateTime.now())
                .build();

        Menu changedMenu = menuRepository.save(changingMenu);
        MenuResponseDto menuResponseDto = modelMapper.map(changedMenu, MenuResponseDto.class);

        menuResponseDto.setRegisteredTime(menu.getRegisteredTime());

        menuResponseDto.setUpdatedTime(changedMenu.getUpdatedTime());

        return menuResponseDto;

    }

    public void deleteMenu(Long id) {

        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 메뉴를 찾을 수 없습니다. (menuId: " + id + ")"));

        menuRepository.delete(menu);

    }

}