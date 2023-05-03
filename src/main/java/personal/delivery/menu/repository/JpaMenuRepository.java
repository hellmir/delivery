package personal.delivery.menu.repository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import personal.delivery.menu.Menu;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class JpaMenuRepository {

    private final MenuRepository menuRepository;

    public Menu insertMenu(Menu menu) {
        Menu savedMenu = menuRepository.save(menu);
        return savedMenu;
    }

    public Menu selectMenu(Long id) {
        Menu selectedMenu = menuRepository.getById(id);
        return selectedMenu;
    }

    public List<Menu> selectAllMenu() {
        List<Menu> allMenu = menuRepository.findAll();

        return allMenu;
    }

    public Menu updateMenu(Menu menu) throws Exception {

        Optional<Menu> selectedMenu = menuRepository.findById(menu.getId());

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
                updatingMenu.importPresentStock(menu.getStock());
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

    public Menu deleteMenu(Long id) throws Exception {
        Optional<Menu> selectedMenu = menuRepository.findById(id);

        Menu deletedMenu;

        if (selectedMenu.isPresent()) {
            Menu menu = selectedMenu.get();
            menuRepository.delete(menu);
            deletedMenu = menu;
        } else {
            throw new EntityNotFoundException();
        }

        return deletedMenu;
    }

}
