package personal.delivery.cart.dto;

import lombok.Data;
import personal.delivery.cart.entity.Cart;
import personal.delivery.menu.Menu;

@Data
public class CartMenuResponseDto {

    private Long id;
    private Cart cart;
    private Menu menu;
    private Integer menuQuantity;

}
