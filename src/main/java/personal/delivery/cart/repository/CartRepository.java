package personal.delivery.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import personal.delivery.cart.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByMemberId(Long memberId);

}
