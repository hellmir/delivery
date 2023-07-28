package personal.delivery.order.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.constant.OrderStatus;
import personal.delivery.constant.Role;
import personal.delivery.exception.FailedToCancelOrderException;
import personal.delivery.member.eneity.Member;
import personal.delivery.member.repository.MemberRepository;
import personal.delivery.menu.Menu;
import personal.delivery.menu.repository.MenuRepository;
import personal.delivery.order.dto.OrderRequestDto;
import personal.delivery.order.dto.OrderResponseDto;
import personal.delivery.order.dto.OrderStatusChangeDto;
import personal.delivery.order.entity.Order;
import personal.delivery.order.entity.Order.OrderBuilder;
import personal.delivery.order.entity.OrderMenu;
import personal.delivery.order.OrderRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final MenuRepository menuRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final OrderMenuService orderMenuService;

    @Override
    public OrderResponseDto takeOrder(OrderRequestDto orderRequestDto) {

        Member member = memberRepository.findByEmail(orderRequestDto.getEmail());

        if (member == null) {
            throw new EntityNotFoundException("해당 회원을 찾을 수 없습니다. (email: " + orderRequestDto.getEmail() + ")");
        }

        Map<Menu, Integer> menuToOrderMap = new HashMap<>();

        for (Long menuId : orderRequestDto.getMenuIdAndQuantityMap().keySet()) {

            menuToOrderMap.put(menuRepository.findById(menuId)
                            .orElseThrow(() -> new EntityNotFoundException
                                    ("해당 메뉴를 찾을 수 없습니다. (menuId: " + menuId + ")"))
                    , orderRequestDto.getMenuIdAndQuantityMap().get(menuId));

        }

        OrderBuilder orderBuilder = createOrder(orderRequestDto, member);

        Order order = orderBuilder.build();

        List<OrderMenu> orderMenuList = orderMenuService.createOrderMenu(menuToOrderMap);

        order = addOrderMenuListToOrder(orderBuilder, orderMenuList);

        Order savedOrder = orderRepository.save(order);

        orderMenuService.updateOrderToOrderMenuList(orderMenuList, savedOrder);

        return setOrderResponseDto(savedOrder);

    }

    private OrderBuilder createOrder(OrderRequestDto orderRequestDto, Member member) {

        return Order.builder()
                .orderTime(LocalDateTime.now())
                .orderStatus(OrderStatus.WAITING)
                .member(member)
                .orderRequest(orderRequestDto.getOrderRequest())
                .deliveryRequest(orderRequestDto.getDeliveryRequest())
                .registrationTime(LocalDateTime.now());

    }

    private Order addOrderMenuListToOrder(OrderBuilder orderBuilder, List<OrderMenu> orderMenuList) {
        return orderBuilder
                .orderMenuList(orderMenuList)
                .totalOrderPrice(orderMenuList.stream().mapToInt(OrderMenu::getTotalMenuPrice).sum())
                .build();
    }

    @Override
    public OrderResponseDto gerOrder(OrderRequestDto orderRequestDto) {

        Member member = memberRepository.findByEmail(orderRequestDto.getEmail());

        if (member == null) {
            throw new EntityNotFoundException("해당 회원을 찾을 수 없습니다. (email: " + orderRequestDto.getEmail() + ")");
        }

        Order selectedOrder = orderRepository.findById(orderRequestDto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException
                        ("해당 주문을 찾을 수 없습니다. (orderId: " + orderRequestDto.getOrderId() + ")"));

        return setOrderResponseDto(selectedOrder);

    }

    @Override
    public OrderResponseDto changeOrderStatus(Long id, OrderStatusChangeDto orderStatusChangeDto) {

        Member member = memberRepository.findByEmail(orderStatusChangeDto.getEmail());

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 주문을 찾을 수 없습니다. (orderId: " + id + ")"));

        if (orderStatusChangeDto.getIsOrderInProgress()) {

            if (member.getRole().equals(Role.SELLER)) {

                if (order.getOrderStatus().equals(OrderStatus.IN_DELIVERY)) {

                    order.updateOrderStatus(OrderStatus.DELIVERED, LocalDateTime.now());

                    order.updateEstimatedArrivalTime(0);

                }

                if (order.getOrderStatus().equals(OrderStatus.COOKING)) {

                    order.updateOrderStatus(OrderStatus.IN_DELIVERY, LocalDateTime.now());

                    order.updateEstimatedArrivalTime(orderStatusChangeDto.getEstimatedRequiredTime());

                }

                if (order.getOrderStatus().equals(OrderStatus.WAITING)) {

                    order.updateOrderStatus(OrderStatus.COOKING, LocalDateTime.now());

                    order.updateEstimatedArrivalTime(orderStatusChangeDto.getEstimatedRequiredTime());

                }

            }

        } else {

            if (member.getRole().equals(Role.SELLER)) {

                if (order.getOrderStatus().equals(OrderStatus.WAITING)) {
                    order.updateOrderStatus(OrderStatus.REFUSED, LocalDateTime.now());
                } else {
                    order.updateOrderStatus(OrderStatus.CANCELED, LocalDateTime.now());
                }

            } else {

                if (order.getOrderStatus().equals(OrderStatus.WAITING)) {
                    order.updateOrderStatus(OrderStatus.CANCELED, LocalDateTime.now());
                } else {
                    throw new FailedToCancelOrderException(
                            "주문이 진행 중인 경우 취소할 수 없습니다. 주문을 취소하려면 판매자에게 문의해야 합니다. 현재 주문 상태: "
                                    + order.getOrderStatus()
                    );
                }

            }

        }

        Order savedOrder = orderRepository.save(order);

        return setOrderResponseDto(savedOrder);

    }

    private OrderResponseDto setOrderResponseDto(Order order) {

        OrderResponseDto orderResponseDto = new OrderResponseDto();

        orderResponseDto.setId(order.getId());
        orderResponseDto.setOrderTime(order.getOrderTime());
        orderResponseDto.setEstimatedArrivalTime(order.getEstimatedArrivalTime());
        orderResponseDto.setOrderStatus(order.getOrderStatus());

        for (OrderMenu orderMenu : order.getOrderMenuList()) {

            List<String> menuDetails = new ArrayList<>();

            menuDetails.add("menuName: " + orderMenu.getMenu().getName());
            menuDetails.add("menuPrice: " + orderMenu.getMenu().getPrice().toString());
            menuDetails.add("orderQuantity: " + orderMenu.getOrderQuantity().toString());
            menuDetails.add("totalMenuPrice: " + orderMenu.getTotalMenuPrice().toString());

            orderResponseDto.addMenuDetails(menuDetails);

        }

        orderResponseDto.setTotalOrderPrice(order.getTotalOrderPrice());
        orderResponseDto.setOrderRequest(order.getOrderRequest());
        orderResponseDto.setDeliveryRequest(order.getDeliveryRequest());
        orderResponseDto.setRegistrationTime(order.getRegistrationTime());
        orderResponseDto.setUpdateTime(order.getUpdateTime());

        return orderResponseDto;

    }

}
