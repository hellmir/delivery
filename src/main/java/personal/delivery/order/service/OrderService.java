package personal.delivery.order.service;

import personal.delivery.order.dto.OrderDto;
import personal.delivery.order.dto.OrderResponseDto;
import personal.delivery.order.dto.OrderStatusDto;

public interface OrderService {

    OrderResponseDto takeOrder(OrderDto orderDto);

    OrderResponseDto gerOrder(OrderDto orderDto);

    OrderResponseDto changeOrderStatus(Long id, OrderStatusDto orderStatusDto);

}
