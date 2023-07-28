package personal.delivery.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import personal.delivery.shop.entity.Shop;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    Shop findByName(String name);
    List<Shop> findByNameContaining(String name);
}
