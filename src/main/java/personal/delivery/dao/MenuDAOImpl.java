package personal.delivery.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import personal.delivery.domain.Menu;
import personal.delivery.repository.MenuRepository;

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

}
