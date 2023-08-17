package personal.delivery.order.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.exception.FailedToCancelOrderException;
import personal.delivery.member.entity.Member;
import personal.delivery.member.repository.MemberRepository;
import personal.delivery.menu.entity.Menu;
import personal.delivery.menu.repository.MenuRepository;
import personal.delivery.order.dto.OrderRequestDto;
import personal.delivery.order.dto.OrderResponseDto;
import personal.delivery.order.dto.OrderStatusChangeDto;
import personal.delivery.order.entity.Order;
import personal.delivery.order.entity.OrderMenu;
import personal.delivery.order.repository.OrderRepository;
import personal.delivery.shop.entity.Shop;
import personal.delivery.shop.repository.ShopRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static personal.delivery.order.constant.OrderStatus.*;
import static personal.delivery.member.constant.Role.SELLER;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ShopRepository shopRepository;
    private final MemberRepository memberRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;
    private final OrderMenuService orderMenuService;

    @Override
    public OrderResponseDto takeOrder(Long shopId, OrderRequestDto orderRequestDto) {

        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new EntityNotFoundException
                        ("해당 가게가 존재하지 않습니다. (memberId: " + shopId + ")"));

        Member member = memberRepository.findById(orderRequestDto.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException
                        ("해당 회원이 존재하지 않습니다. (memberId: " + orderRequestDto.getMemberId() + ")"));

        Map<Menu, Integer> menuToOrderMap = createMenuToOrderMap(orderRequestDto);

        List<OrderMenu> orderMenuList = orderMenuService.createOrderMenu(menuToOrderMap);

        Order order = createOrder(orderRequestDto, shop, member, orderMenuList);

        Order savedOrder = orderRepository.save(order);

        orderMenuService.updateOrderToOrderMenuList(orderMenuList, savedOrder);

        return setOrderResponseDto(savedOrder);

    }

    @Override
    public OrderResponseDto gerOrder(OrderRequestDto orderRequestDto) {

        Member member = memberRepository.findById(orderRequestDto.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException
                        ("해당 회원이 존재하지 않습니다. (memberId: " + orderRequestDto.getMemberId() + ")"));

        Order selectedOrder = orderRepository.findById(orderRequestDto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException
                        ("해당 주문이 존재하지 않습니다. (orderId: " + orderRequestDto.getOrderId() + ")"));

        return setOrderResponseDto(selectedOrder);

    }

    @Override
    public OrderResponseDto changeOrderStatus(Long shopId, Long id, OrderStatusChangeDto orderStatusChangeDto) {

        Member member = memberRepository.findById(orderStatusChangeDto.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException
                        ("해당 회원이 존재하지 않습니다. (memberId: " + orderStatusChangeDto.getMemberId() + ")"));

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 주문이 존재하지 않습니다. (orderId: " + id + ")"));

        if (member.getRole().equals(SELLER)) {

            order = orderStatusChangeDto.getIsOrderInProgress()
                    ? progressOrder(order, orderStatusChangeDto) : disContinueOrder(order);

        } else if (!orderStatusChangeDto.getIsOrderInProgress()) {

            order = cancelOrder(order);

        } else {

            throw new SecurityException("고객은 주문 진행 권한이 없습니다.");

        }

        Order savedOrder = orderRepository.save(order);

        return setOrderResponseDto(savedOrder);

    }

    private Map<Menu, Integer> createMenuToOrderMap(OrderRequestDto orderRequestDto) {

        Map<Menu, Integer> menuToOrderMap = new HashMap<>();

        for (Long menuId : orderRequestDto.getMenuIdAndQuantityMap().keySet()) {

            menuToOrderMap.put(menuRepository.findById
                            (menuId).orElseThrow(() -> new EntityNotFoundException
                            ("해당 메뉴가 존재하지 않습니다. (menuId: " + menuId + ")"))
                    , orderRequestDto.getMenuIdAndQuantityMap().get(menuId));

        }

        return menuToOrderMap;

    }

    private Order createOrder
            (OrderRequestDto orderRequestDto, Shop shop, Member member, List<OrderMenu> orderMenuList) {

        return Order.builder()
                .orderTime(LocalDateTime.now())
                .orderStatus(WAITING)
                .shop(shop)
                .member(member)
                .orderMenuList(orderMenuList)
                .totalOrderPrice(orderMenuList.stream().mapToInt(OrderMenu::getTotalMenuPrice).sum())
                .orderRequest(orderRequestDto.getOrderRequest())
                .deliveryRequest(orderRequestDto.getDeliveryRequest())
                .build();

    }

    private Order progressOrder(Order order, OrderStatusChangeDto orderStatusChangeDto) {

        if (order.getOrderStatus().equals(WAITING)) {

            order.updateOrderStatus(COOKING);

            order.updateEstimatedArrivalTime(orderStatusChangeDto.getEstimatedRequiredTime());

        } else if (order.getOrderStatus().equals(COOKING)) {

            order.updateOrderStatus(IN_DELIVERY);

            order.updateEstimatedArrivalTime(orderStatusChangeDto.getEstimatedRequiredTime());

        } else {

            order.updateOrderStatus(DELIVERED);

            order.updateEstimatedArrivalTime(0);

        }

        return order;

    }

    private Order disContinueOrder(Order order) {

        if (order.getOrderStatus().equals(WAITING)) {
            order.updateOrderStatus(REFUSED);
        } else if (!order.getOrderStatus().equals(REFUNDED)) {
            order.updateOrderStatus(CANCELED);
        }

        return order;

    }

    private Order cancelOrder(Order order) {

        if (order.getOrderStatus().equals(WAITING)) {
            order.updateOrderStatus(CANCELED);
        } else {
            throw new FailedToCancelOrderException
                    ("주문이 진행 중인 경우 취소할 수 없습니다. 주문을 취소하려면 판매자에게 문의해야 합니다. 현재 주문 상태: "
                            + order.getOrderStatus());
        }

        return order;

    }

    private OrderResponseDto setOrderResponseDto(Order order) {

        OrderResponseDto orderResponseDto = new OrderResponseDto();

        orderResponseDto.setId(order.getId());
        orderResponseDto.setShopName(order.getShop().getName());
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
        orderResponseDto.setRegisteredTime(order.getRegisteredTime());
        orderResponseDto.setUpdatedTime(order.getUpdatedTime());

        return orderResponseDto;

    }

}
