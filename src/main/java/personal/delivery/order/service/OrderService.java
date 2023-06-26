package personal.delivery.order.service;

import personal.delivery.member.domain.Member;
import personal.delivery.order.dto.OrderDto;
import personal.delivery.order.dto.OrderResponseDto;
import personal.delivery.order.entity.Order;
import personal.delivery.order.entity.OrderMenu;

import java.util.List;

public interface OrderService {

    OrderResponseDto takeOrder(OrderDto orderDto);

    Order createOrder(OrderDto orderDto, Member member, List<OrderMenu> orderMenuList);

    OrderResponseDto gerOrder(OrderDto orderDto);

}
