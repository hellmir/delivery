package personal.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import personal.delivery.domain.Menu;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Optional<Menu> findById(Long id);
}