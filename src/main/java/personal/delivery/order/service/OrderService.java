package personal.delivery.order.service;

import personal.delivery.member.Member;
import personal.delivery.order.dto.OrderDto;
import personal.delivery.order.dto.OrderResponseDto;
import personal.delivery.order.entity.Order;
import personal.delivery.order.entity.OrderMenu;

import java.util.List;

public interface OrderService {

    OrderResponseDto takeOrder(OrderDto orderDto);

    Order createOrder(Member member, List<OrderMenu> orderMenuList);

    Order takeOrders(List<OrderDto> orderDtoList);

    int getMenuListTotalPrice(List<OrderMenu> orderMenus);

}
