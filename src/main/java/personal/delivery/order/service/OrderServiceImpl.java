package personal.delivery.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.constant.OrderStatus;
import personal.delivery.member.Member;
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

    @Override
    public OrderResponseDto takeOrder(OrderDto orderDto) {

        Menu menu = menuRepository.getReferenceById(orderDto.getMenuId());
        Member member = memberRepository.findByEmail(orderDto.getEmail());

        List<OrderMenu> orderMenuList = new ArrayList<>();
        OrderMenu orderMenu = orderMenuService.createOrderMenu(menu, orderDto.getOrderQuantity());
        orderMenuList.add(orderMenu);

        Order order = createOrder(member, orderMenuList);

        Order savedOrder = orderRepository.save(order);

        return setOrderResponseDto(savedOrder);

    }

    @Override
    public Order createOrder(Member member, List<OrderMenu> orderMenuList) {

        Order order = Order.builder()
                .orderTime(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDER)
                .member(member)
                .orderMenus(orderMenuList)
                .registrationTime(LocalDateTime.now())
                .build();

        List<OrderMenu> orderMenus = order.getOrderMenus();

        order.computeTotalPrice(getMenuListTotalPrice(orderMenus));

        return order;

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
        orderResponseDto.setRegistrationTime(savedOrder.getRegistrationTime());

        return orderResponseDto;

    }

}
