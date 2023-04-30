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

            updatingMenu.updateMenu(menu.getName(), menu.getPrice(), menu.getSalesRate(), menu.getFlavor(),
                    menu.getPortions(), menu.getCookingTime(), menu.getMenuType(), menu.getFoodType());

            if (menu.getStock() > 0) {
                updatingMenu.importPresentStock(menu.getStock());
            }

            updatedMenu = menuRepository.save(updatingMenu);

        } else {
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
