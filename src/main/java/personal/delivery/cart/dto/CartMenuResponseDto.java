package personal.delivery.cart.dto;

import lombok.Data;
import personal.delivery.menu.entity.Menu;

import java.time.LocalDateTime;

@Data
public class CartMenuResponseDto {

    private String shopName;
    private Long cartMenuId;
    private Menu menu;
    private Integer menuPrice;
    private Integer menuQuantity;
    private Integer totalCartMenuPrice;

    private LocalDateTime registeredTime;
    private LocalDateTime updatedTime;

}
