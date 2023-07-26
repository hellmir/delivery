package personal.delivery.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import personal.delivery.shop.entity.Shop;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    Shop findByName(String name);
}
