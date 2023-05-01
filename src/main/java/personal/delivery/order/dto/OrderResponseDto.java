package personal.delivery.order.dto;

import lombok.Data;
import personal.delivery.constant.OrderStatus;
import personal.delivery.member.Member;
import personal.delivery.order.entity.OrderMenu;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDto {

    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Member member;
    private List<OrderMenu> orderMenuList;
    private int totalPrice;

    private LocalDateTime regTime;
    private LocalDateTime updateTime;

}
