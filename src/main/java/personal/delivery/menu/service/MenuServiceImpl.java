package personal.delivery.menu.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.config.BeanConfiguration;
import personal.delivery.menu.Menu;
import personal.delivery.menu.dto.MenuDto;
import personal.delivery.menu.dto.MenuResponseDto;
import personal.delivery.menu.repository.MenuRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final BeanConfiguration beanConfiguration;

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
                .registrationTime(LocalDateTime.now())
                .build();

        Menu savedMenu = menuRepository.save(menu);

        MenuResponseDto menuResponseDto = beanConfiguration.modelMapper()
                .map(savedMenu, MenuResponseDto.class);

        return menuResponseDto;

    }

    @Override
    public MenuResponseDto getMenu(Long id) {

        Menu selectedMenu = menuRepository.getById(id);

        MenuResponseDto menuResponseDto = beanConfiguration.modelMapper()
                .map(selectedMenu, MenuResponseDto.class);

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

    @Transactional
    @Override
    public MenuResponseDto changeMenu(Long id, MenuDto menuDto) throws Exception {

        Menu menu = Menu.builder()
                .id(id)
                .name(menuDto.getName())
                .price(menuDto.getPrice())
                .salesRate(menuDto.getSalesRate())
                .stock(menuDto.getStock())
                .flavor(menuDto.getFlavor())
                .portions(menuDto.getPortions())
                .cookingTime(menuDto.getCookingTime())
                .menuType(menuDto.getMenuType())
                .foodType(menuDto.getFoodType())
                .build();

        Optional<Menu> selectedMenu = menuRepository.findById(id);

        Menu changedMenu = pickColumnToChange(menu, selectedMenu);

        MenuResponseDto menuResponseDto = beanConfiguration.modelMapper()
                .map(changedMenu, MenuResponseDto.class);

        return menuResponseDto;

    }

    public MenuResponseDto deleteMenu(Long id) throws Exception {

        Optional<Menu> selectedMenu = menuRepository.findById(id);
        Menu deletedMenu;

        if (selectedMenu.isPresent()) {
            Menu menu = selectedMenu.get();
            menuRepository.delete(menu);
            deletedMenu = menu;
        } else {
            throw new EntityNotFoundException();
        }

        MenuResponseDto menuResponseDto = beanConfiguration.modelMapper()
                .map(deletedMenu, MenuResponseDto.class);

        return menuResponseDto;

    }

    private Menu pickColumnToChange(Menu menu, Optional<Menu> selectedMenu) {

        Menu updatedMenu;

        if (selectedMenu.isPresent()) {

            Menu updatingMenu = selectedMenu.get();

            if (menu.getName() != null) {
                updatingMenu.updateName(menu.getName());
            }

            if (menu.getPrice() > 0) {
                updatingMenu.updatePrice(menu.getPrice());
            }

            if (menu.getSalesRate() > 0) {
                updatingMenu.updateSalesRate(menu.getSalesRate());
            }

            if (menu.getStock() > 0) {
                updatingMenu.addStock(menu.getStock());
            }

            if (menu.getFlavor() != null) {
                updatingMenu.updateFlavor(menu.getFlavor());
            }

            if (menu.getPortions() > 0) {
                updatingMenu.updatePortions(menu.getPortions());
            }

            if (menu.getCookingTime() > 0) {
                updatingMenu.updateCookingTime(menu.getCookingTime());
            }

            if (menu.getMenuType() != null) {
                updatingMenu.updateMenuType(menu.getMenuType());
            }

            if (menu.getFoodType() != null) {
                updatingMenu.updateFoodType(menu.getFoodType());
            }

            updatingMenu.updateTime(LocalDateTime.now());

            updatedMenu = menuRepository.save(updatingMenu);

        } else {
            throw new EntityNotFoundException();
        }

        return updatedMenu;

    }

}