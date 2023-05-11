package personal.delivery.order.dto;

import lombok.Data;
import personal.delivery.constant.OrderStatus;

import java.time.LocalDateTime;

@Data
public class OrderResponseDto {

    private LocalDateTime orderTime;
    private OrderStatus orderStatus;
    private String memberName;
    private String memberEmail;
    private String menuName;
    private int cookingTime;
    private int menuPrice;
    private int orderQuantity;
    private int totalPrice;

    private LocalDateTime registrationTime;
    private LocalDateTime updateTime;

}
