package personal.delivery.order.service;

import personal.delivery.order.dto.OrderRequestDto;
import personal.delivery.order.dto.OrderResponseDto;
import personal.delivery.order.dto.OrderStatusChangeDto;

public interface OrderService {

    OrderResponseDto takeOrder(OrderRequestDto orderRequestDto);

    OrderResponseDto gerOrder(OrderRequestDto orderRequestDto);

    OrderResponseDto changeOrderStatus(Long id, OrderStatusChangeDto orderStatusChangeDto);

}
