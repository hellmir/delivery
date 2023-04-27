package personal.delivery.menu.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import personal.delivery.menu.repository.MenuDAO;
import personal.delivery.menu.Menu;
import personal.delivery.menu.repository.MenuRepository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class MenuDAOImpl implements MenuDAO {

    private final MenuRepository menuRepository;

    @Override
    public Menu insertMenu(Menu menu) {
        Menu savedMenu = menuRepository.save(menu);
        return savedMenu;
    }

    @Override
    public Menu selectMenu(Long id) {
        Menu selectedMenu = menuRepository.getById(id);
        return selectedMenu;
    }

    @Override
    public List<Menu> selectAllMenu() {
        List<Menu> allMenu = menuRepository.findAll();

        return allMenu;
    }

    @Override
    public Menu updateMenu(Menu menu) throws Exception {

        Optional<Menu> selectedMenu = menuRepository.findById(menu.getId());

        Menu updatedMenu;

        if (selectedMenu.isPresent()) {

            Menu updatingMenu = selectedMenu.get();

            if (menu.getName() != null) {
                updatingMenu.setName(menu.getName());
            }

            if (menu.getPrice() > 0) {
                updatingMenu.setPrice(menu.getPrice());
            }

            if (menu.getStock() > 0) {
                updatingMenu.setStock(updatingMenu.getStock() + menu.getStock());
            }

            int presentStock = updatingMenu.getStock();

            if (presentStock > 0) {
                updatingMenu.setName(updatingMenu.getName().replace("(재료 소진)", ""));
            }

            if (menu.getSalesRate() > 0) {
                if (presentStock >= menu.getSalesRate()) {
                    updatingMenu.setSalesRate(updatingMenu.getSalesRate() + menu.getSalesRate());
                    updatingMenu.setStock(presentStock - menu.getSalesRate());

                    if (presentStock == menu.getSalesRate()) {
                        updatingMenu.setName(updatingMenu.getName() + "(재료 소진)");
                    }
                }
            }

            // 판매량 초기화
            if (menu.getSalesRate() == -1) {
                updatingMenu.setSalesRate(0);
            }

            if (menu.getFlavor() != null) {
                updatingMenu.setFlavor(menu.getFlavor());
            }

            if (menu.getPortions() > 0) {
                updatingMenu.setPortions(menu.getPortions());
            }

            if (menu.getCookingTime() > 0) {
                updatingMenu.setCookingTime(menu.getCookingTime());
            }

            if (menu.getMenuType() != null) {
                updatingMenu.setMenuType(menu.getMenuType());
            }

            if (menu.getFoodType() != null) {
                updatingMenu.setFoodType(menu.getFoodType());
            }


            updatingMenu.setName(updatingMenu.getName().replace("(인기메뉴)", ""));

            if (updatingMenu.getSalesRate() >= 100) {
                updatingMenu.setPopularMenu(true);
                updatingMenu.setName(updatingMenu.getName() + "(인기메뉴)");
            }

            updatedMenu = menuRepository.save(updatingMenu);

            if (presentStock < menu.getSalesRate()) {
                updatedMenu.setName(updatedMenu.getName() + "(판매 불가 : 재료 부족)");
            }

        } else

        {
            throw new Exception();
        }

        return updatedMenu;

    }


    @Override
    public String deleteMenu(Long id) throws Exception {
        Optional<Menu> selectedMenu = menuRepository.findById(id);

        if (selectedMenu.isPresent()) {
            Menu menu = selectedMenu.get();
            menuRepository.delete(menu);
        } else {
            throw new Exception();
        }

        return selectedMenu.get().getName();
    }

}