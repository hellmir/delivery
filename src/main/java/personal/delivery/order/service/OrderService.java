package personal.delivery.order.service;

import personal.delivery.menu.dto.MenuResponseDto;
import personal.delivery.order.dto.OrderRequestDto;
import personal.delivery.order.dto.OrderResponseDto;
import personal.delivery.order.dto.OrderStatusChangeDto;

import java.util.List;

public interface OrderService {

    OrderResponseDto takeOrder(Long shopId, OrderRequestDto orderRequestDto);

    OrderResponseDto gerOrder(OrderRequestDto orderRequestDto);

    OrderResponseDto changeOrderStatus(Long shopId, Long id, OrderStatusChangeDto orderStatusChangeDto);

}
