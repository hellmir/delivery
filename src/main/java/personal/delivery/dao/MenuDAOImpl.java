package personal.delivery.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import personal.delivery.domain.Menu;
import personal.delivery.repository.MenuRepository;


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

}
