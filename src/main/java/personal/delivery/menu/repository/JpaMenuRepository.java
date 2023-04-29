package personal.delivery.menu.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import personal.delivery.menu.Menu;

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
                updatingMenu.changeName(menu.getName());
            }

            if (menu.getPrice() > 0) {
                updatingMenu.changePrice(menu.getPrice());
            }

            if (menu.getStock() > 0) {
                updatingMenu.importPresentStock(menu.getStock());
            }

            if (menu.getSalesRate() > 0) {
                updatingMenu.changeSalesRate(menu.getSalesRate());
            } else if (menu.getSalesRate() == -1) {
                updatingMenu.changeSalesRate(0);
            }

            if (menu.getFlavor() != null) {
                updatingMenu.changeFlavor(menu.getFlavor());
            }

            if (menu.getPortions() > 0) {
                updatingMenu.changePortions(menu.getPortions());
            }

            if (menu.getCookingTime() > 0) {
                updatingMenu.changeCookingTime(menu.getCookingTime());
            }

            if (menu.getMenuType() != null) {
                updatingMenu.changeMenuType(menu.getMenuType());
            }

            if (menu.getFoodType() != null) {
                updatingMenu.changeFoodType(menu.getFoodType());
            }


            updatingMenu.changeName(updatingMenu.getName().replace("(인기메뉴)", ""));

            if (updatingMenu.getSalesRate() >= 100) {
                updatingMenu.changePopularMenu(true);
                updatingMenu.changeName(updatingMenu.getName() + "(인기메뉴)");
            }

            updatedMenu = menuRepository.save(updatingMenu);

        } else

        {
            throw new Exception();
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
            throw new Exception();
        }

        return deletedMenu;
    }

}
