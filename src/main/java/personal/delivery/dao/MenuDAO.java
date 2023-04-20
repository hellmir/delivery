package personal.delivery.dao;

import personal.delivery.domain.Menu;

public interface MenuDAO {

    Menu insertMenu(Menu menu);

    Menu selectMenu(Long id);

}
