package personal.delivery.dao;

import personal.delivery.domain.Menu;

import java.util.List;

public interface MenuDAO {

    Menu insertMenu(Menu menu);

    Menu selectMenu(Long id);

    List<Menu> selectAllMenu();

    Menu updateMenu(Menu menu) throws Exception;

    String deleteMenu(Long id) throws Exception;

}