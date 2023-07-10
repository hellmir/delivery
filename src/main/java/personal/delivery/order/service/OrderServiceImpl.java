package personal.delivery.order.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.config.BeanConfiguration;
import personal.delivery.constant.OrderStatus;
import personal.delivery.constant.Role;
import personal.delivery.member.eneity.Member;
import personal.delivery.member.repository.MemberRepository;
import personal.delivery.menu.Menu;
import personal.delivery.menu.repository.MenuRepository;
import personal.delivery.order.OrderRepository;
import personal.delivery.order.dto.OrderDto;
import personal.delivery.order.dto.OrderResponseDto;
import personal.delivery.order.entity.Order;
import personal.delivery.order.entity.OrderMenu;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final MenuRepository menuRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final OrderMenuService orderMenuService;
    private final BeanConfiguration beanConfiguration;

    @Override
    public OrderResponseDto takeOrder(OrderDto orderDto) {

        Menu menu = menuRepository.findById(orderDto.getMenuId())
                .orElseThrow(() -> new EntityNotFoundException
                        ("해당 메뉴를 찾을 수 없습니다. (menuId: " + orderDto.getMenuId() + ")"));

        Member member = memberRepository.findByEmail(orderDto.getEmail());

        if (member == null) {
            throw new EntityNotFoundException("해당 회원을 찾을 수 없습니다. (email: " + orderDto.getEmail() + ")");
        }

        List<OrderMenu> orderMenuList = new ArrayList<>();
        OrderMenu orderMenu = orderMenuService.createOrderMenu(menu, orderDto.getOrderQuantity());
        orderMenuList.add(orderMenu);

        Order order = createOrder(orderDto, member, orderMenuList);

        Order savedOrder = orderRepository.save(order);

        return setOrderResponseDto(savedOrder);

    }

    @Override
    public Order createOrder(OrderDto orderDto, Member member, List<OrderMenu> orderMenuList) {

        Order order = Order.builder()
                .orderTime(LocalDateTime.now())
                .orderStatus(OrderStatus.WAITING)
                .member(member)
                .orderMenus(orderMenuList)
                .orderRequest(orderDto.getOrderRequest())
                .deliveryRequest(orderDto.getDeliveryRequest())
                .registrationTime(LocalDateTime.now())
                .build();

        List<OrderMenu> orderMenus = order.getOrderMenus();

        order.computeTotalPrice(getMenuListTotalPrice(orderMenus));

        return order;

    }

    @Override
    public OrderResponseDto gerOrder(OrderDto orderDto) {

        Member member = memberRepository.findByEmail(orderDto.getEmail());

        Order selectedOrder = orderRepository.findById(orderDto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException
                        ("해당 주문을 찾을 수 없습니다. (orderId: " + orderDto.getOrderId() + ")"));

        OrderResponseDto orderResponseDto = new OrderResponseDto();

        orderResponseDto.setOrderTime(selectedOrder.getOrderTime());
        orderResponseDto.setOrderStatus(selectedOrder.getOrderStatus());
        orderResponseDto.setMemberName(selectedOrder.getMember().getName());
        orderResponseDto.setMemberEmail(selectedOrder.getMember().getEmail());

        for (OrderMenu orderMenu : selectedOrder.getOrderMenus()) {

            orderResponseDto.setMenuName(orderMenu.getMenu().getName());
            orderResponseDto.setCookingTime(orderMenu.getMenu().getCookingTime());
            orderResponseDto.setMenuPrice(orderMenu.getMenu().getPrice());
            orderResponseDto.setOrderQuantity(orderMenu.getOrderQuantity());
            orderResponseDto.setTotalPrice(orderMenu.getMenuTotalPrice());

        }

        orderResponseDto.setRegistrationTime(selectedOrder.getRegistrationTime());
        orderResponseDto.setUpdateTime(selectedOrder.getUpdateTime());

        return orderResponseDto;

    }

    @Override
    public OrderResponseDto changeOrderStatus(Long id, Boolean isOrdered) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 주문을 찾을 수 없습니다. (orderId: " + id + ")"));

        if (isOrdered) {
            if (order.getOrderStatus().equals(OrderStatus.WAITING)) {
                order.updateOrderStatus(OrderStatus.ACCEPTED, LocalDateTime.now());
            } else if (order.getOrderStatus().equals(OrderStatus.ACCEPTED)) {
                order.updateOrderStatus(OrderStatus.COOKING, LocalDateTime.now());
            } else if (order.getOrderStatus().equals(OrderStatus.COOKING)) {
                order.updateOrderStatus(OrderStatus.IN_DELIVERY, LocalDateTime.now());
            } else if (order.getOrderStatus().equals(OrderStatus.IN_DELIVERY)) {
                order.updateOrderStatus(OrderStatus.DELIVERED, LocalDateTime.now());
            }

        } else {

            if (order.getOrderStatus().equals(OrderStatus.WAITING)) {

                if (order.getMember().getRole().equals(Role.SELLER)) {
                    order.updateOrderStatus(OrderStatus.REFUSED, LocalDateTime.now());
                } else if (order.getMember().getRole().equals(Role.CUSTOMER)) {
                    order.updateOrderStatus(OrderStatus.CANCELED, LocalDateTime.now());
                }

            } else if (order.getOrderStatus().equals(OrderStatus.ACCEPTED)
                    || order.getOrderStatus().equals(OrderStatus.COOKING)) {
                order.updateOrderStatus(OrderStatus.CANCELED, LocalDateTime.now());
            }

        }

        Order savedOrder = orderRepository.save(order);

        return setOrderResponseDto(savedOrder);

    }

    public int getMenuListTotalPrice(List<OrderMenu> orderMenus) {

        int totalPrice = 0;

        for (OrderMenu orderMenu : orderMenus) {

            totalPrice += orderMenu.getMenuTotalPrice();

        }

        return totalPrice;

    }

    private OrderResponseDto setOrderResponseDto(Order savedOrder) {

        OrderResponseDto orderResponseDto = new OrderResponseDto();

        orderResponseDto.setOrderTime(savedOrder.getOrderTime());
        orderResponseDto.setOrderStatus(savedOrder.getOrderStatus());
        orderResponseDto.setMemberName(savedOrder.getMember().getName());
        orderResponseDto.setMemberEmail(savedOrder.getMember().getEmail());

        for (OrderMenu orderMenu : savedOrder.getOrderMenus()) {

            orderResponseDto.setMenuName(orderMenu.getMenu().getName());
            orderResponseDto.setCookingTime(orderMenu.getMenu().getCookingTime());
            orderResponseDto.setMenuPrice(orderMenu.getMenu().getPrice());
            orderResponseDto.setOrderQuantity(orderMenu.getOrderQuantity());

        }

        orderResponseDto.setTotalPrice(savedOrder.getTotalPrice());
        orderResponseDto.setOrderRequest(savedOrder.getOrderRequest());
        orderResponseDto.setDeliveryRequest(savedOrder.getDeliveryRequest());
        orderResponseDto.setRegistrationTime(savedOrder.getRegistrationTime());
        orderResponseDto.setUpdateTime(savedOrder.getUpdateTime());

        return orderResponseDto;

    }

}
