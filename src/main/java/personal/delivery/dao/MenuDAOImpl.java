package personal.delivery.dao;

import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PatchMapping;
import personal.delivery.domain.Menu;
import personal.delivery.repository.MenuRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;


// 1. MenuService로부터 요청값 받음
// 2. SimpleJpaRepository의 메소드를 호출해서 CRUD 수행
// 3. 갱신된 Menu 엔티티 값을 응답값으로 return
@Component
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
    public List<Menu> selectAllMenu() throws Exception {
        List<Menu> allMenu = menuRepository.findAll();
        List<Menu> selectedMenu = new ArrayList<>();

        for (Menu all : allMenu) {
            if (all != null) {
                selectedMenu.add(all);
            } else {
                throw new Exception();
            }
        }
        return selectedMenu;
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

            if (menu.getSalesRate() > 0) {
                if (updatingMenu.getStock() >= menu.getSalesRate()) {
                    updatingMenu.setSalesRate(updatingMenu.getSalesRate() + menu.getSalesRate());
                    updatingMenu.setStock(updatingMenu.getStock() - menu.getSalesRate());
                    if (updatingMenu.getStock() == menu.getSalesRate()) {
                        updatingMenu.setName(updatingMenu.getName() + "(재고 소진)");
                    }
                } else {
                    updatingMenu.setName(updatingMenu.getName() + "(판매 불가 : 재고 부족)");
                }
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

            if (updatingMenu.getSalesRate() >= 100) {
                updatingMenu.setPopularMenu(true);
                updatingMenu.setName(updatingMenu.getName() + "(인기메뉴)");
            }

            updatedMenu = menuRepository.save(updatingMenu);

        } else {
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
