package personal.delivery.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import personal.delivery.menu.entity.Menu;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByShop_Id(Long shopId);
}