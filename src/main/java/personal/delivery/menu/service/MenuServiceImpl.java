package personal.delivery.menu.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.exception.OutOfStockException;
import personal.delivery.menu.dto.MenuRequestDto;
import personal.delivery.menu.dto.MenuResponseDto;
import personal.delivery.menu.entity.Menu;
import personal.delivery.menu.repository.MenuRepository;
import personal.delivery.shop.entity.Shop;
import personal.delivery.shop.repository.ShopRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
                .orElseThrow(() -> new EntityNotFoundException("해당 가게가 존재하지 않습니다. (shopId: " + shopId + ")"));

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

        MenuResponseDto menuResponseDto = modelMapper.map(savedMenu, MenuResponseDto.class);
        menuResponseDto.setShopId(shopId);
        menuResponseDto.setShopName(shop.getName());

        return menuResponseDto;

    }

    @Override
    public List<MenuResponseDto> getAllMenus() {

        List<Menu> menus = menuRepository.findAll();

        List<MenuResponseDto> menuResponseDtoList = new ArrayList<>();

        for (Menu menu : menus) {

            MenuResponseDto menuResponseDto = modelMapper.map(menu, MenuResponseDto.class);

            menuResponseDto.setShopId(menu.getShop().getId());
            menuResponseDto.setShopName(menu.getShop().getName());

            menuResponseDtoList.add(menuResponseDto);

        }

        return menuResponseDtoList;

    }

    @Override
    public MenuResponseDto getMenu(Long shopId, Long id) {

        shopRepository.findById(shopId)
                .orElseThrow(() -> new EntityNotFoundException
                        ("해당 가게가 존재하지 않습니다. (shopId: " + shopId + ")"));

        Menu menu = menuRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        MenuResponseDto menuResponseDto = modelMapper.map(menu, MenuResponseDto.class);
        menuResponseDto.setShopId(shopId);
        menuResponseDto.setShopName(menu.getShop().getName());

        return menuResponseDto;

    }

    @Override
    public MenuResponseDto updateMenu(Long shopId, Long id, MenuRequestDto menuRequestDto) {

        shopRepository.findById(shopId)
                .orElseThrow(() -> new EntityNotFoundException
                        ("해당 가게가 존재하지 않습니다. (shopId: " + shopId + ")"));

        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 메뉴가 존재하지 않습니다. (menuId: " + id + ")"));

        int modifiedStock = menu.getStock() + menuRequestDto.getStock();

        if (modifiedStock < 0) {
            throw new OutOfStockException("재료 최소 수량: 0 (현재 재고: " + menu.getStock() + ")");
        }

        Menu updatingMenu = menu.builder()
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

        Menu updatedMenu = menuRepository.save(updatingMenu);

        MenuResponseDto menuResponseDto = modelMapper.map(updatedMenu, MenuResponseDto.class);
        menuResponseDto.setShopId(shopId);
        menuResponseDto.setShopName(updatedMenu.getShop().getName());

        return menuResponseDto;

    }

    public void deleteMenu(Long shopId, Long id) {

        shopRepository.findById(shopId)
                .orElseThrow(() -> new EntityNotFoundException
                        ("해당 가게가 존재하지 않습니다. (shopId: " + shopId + ")"));

        menuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException
                        ("해당 메뉴가 존재하지 않습니다. (menuId: " + id + ")"));

        menuRepository.deleteById(id);

    }

    @Override
    public void deleteSeveralMenus(Long shopId, List<Long> ids) {

        shopRepository.findById(shopId)
                .orElseThrow(() -> new EntityNotFoundException
                        ("해당 가게가 존재하지 않습니다. (shopId: " + shopId + ")"));

        List<Menu> menus = menuRepository.findByIdIn(ids);

        if (menus.size() == ids.size()) {
            menuRepository.deleteByIdIn(ids);
        } else {
            throw new EntityNotFoundException("존재하지 않는 메뉴가 포함되어 있습니다.");
        }

    }

}