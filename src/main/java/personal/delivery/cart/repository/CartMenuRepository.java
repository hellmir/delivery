package personal.delivery.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import personal.delivery.cart.entity.CartMenu;

public interface CartMenuRepository extends JpaRepository<CartMenu, Long> {

    CartMenu findByCartIdAndMenuId(@Param("cart_Id") Long cartId, @Param("menu_Id") Long menuId);

}
