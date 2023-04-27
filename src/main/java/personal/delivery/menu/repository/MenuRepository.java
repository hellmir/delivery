package personal.delivery.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import personal.delivery.menu.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}