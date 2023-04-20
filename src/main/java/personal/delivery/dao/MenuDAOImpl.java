package personal.delivery.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import personal.delivery.domain.Menu;
import personal.delivery.repository.MenuRepository;

@Component
public class MenuDAOImpl implements MenuDAO {

    private final MenuRepository menuRepository;

    @Autowired
    public MenuDAOImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

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

}
