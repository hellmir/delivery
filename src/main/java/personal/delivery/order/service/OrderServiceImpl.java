package personal.delivery.order.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.config.BeanConfiguration;
import personal.delivery.constant.OrderStatus;
import personal.delivery.constant.Role;
import personal.delivery.member.domain.Member;
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
import java.util.Optional;

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

        Menu menu = menuRepository.getReferenceById(orderDto.getMenuId());
        Member member = memberRepository.findByEmail(orderDto.getEmail());

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

        Optional<Order> order = orderRepository.findById(orderDto.getOrderId());

        Order selectedOrder;

        if (order.isPresent()) {
            selectedOrder = order.get();
        } else {
            throw new EntityNotFoundException();
        }

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

        Optional<Order> order = orderRepository.findById(id);

        if (order.isPresent()) {

            if (isOrdered) {
                if (order.get().getOrderStatus().equals(OrderStatus.WAITING)) {
                    order.get().updateOrderStatus(OrderStatus.ACCEPTED, LocalDateTime.now());
                } else if (order.get().getOrderStatus().equals(OrderStatus.ACCEPTED)) {
                    order.get().updateOrderStatus(OrderStatus.COOKING, LocalDateTime.now());
                } else if (order.get().getOrderStatus().equals(OrderStatus.COOKING)) {
                    order.get().updateOrderStatus(OrderStatus.IN_DELIVERY, LocalDateTime.now());
                } else if (order.get().getOrderStatus().equals(OrderStatus.IN_DELIVERY)) {
                    order.get().updateOrderStatus(OrderStatus.DELIVERED, LocalDateTime.now());
                }

            } else {

                if (order.get().getOrderStatus().equals(OrderStatus.WAITING)) {

                    if (order.get().getMember().getRole().equals(Role.SELLER)) {
                        order.get().updateOrderStatus(OrderStatus.REFUSED, LocalDateTime.now());
                    } else if (order.get().getMember().getRole().equals(Role.CUSTOMER)) {
                        order.get().updateOrderStatus(OrderStatus.CANCELED, LocalDateTime.now());
                    }

                } else if (order.get().getOrderStatus().equals(OrderStatus.ACCEPTED)) {
                    order.get().updateOrderStatus(OrderStatus.CANCELED, LocalDateTime.now());
                } else if (order.get().getOrderStatus().equals(OrderStatus.COOKING)) {
                    order.get().updateOrderStatus(OrderStatus.CANCELED, LocalDateTime.now());
                }

            }

        } else {
            throw new EntityNotFoundException();
        }

        Order savedOrder = orderRepository.save(order.get());

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
