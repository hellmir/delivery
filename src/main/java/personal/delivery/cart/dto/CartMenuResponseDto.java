package personal.delivery.cart.dto;

import lombok.Data;
import personal.delivery.cart.entity.Cart;
import personal.delivery.menu.Menu;

import java.time.LocalDateTime;

@Data
public class CartMenuResponseDto {

    private Long id;
    private Cart cart;
    private Menu menu;
    private int menuQuantity;
    private LocalDateTime registeredTime;
    private LocalDateTime updatedTime;

}
