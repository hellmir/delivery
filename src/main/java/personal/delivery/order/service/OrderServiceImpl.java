package personal.delivery.order.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.config.BeanConfiguration;
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

        Order order = createOrder(member, orderMenuList);

        Order savedOrder = orderRepository.save(order);

        return setOrderResponseDto(savedOrder);

    }

    @Override
    public Order createOrder(Member member, List<OrderMenu> orderMenuList) {

        Order order = Order.builder()
                .orderTime(LocalDateTime.now())
                .orderStatus(OrderStatus.WAITING)
                .member(member)
                .orderMenus(orderMenuList)
                .registrationTime(LocalDateTime.now())
                .build();

        List<OrderMenu> orderMenus = order.getOrderMenus();

        order.computeTotalPrice(getMenuListTotalPrice(orderMenus));

        return order;

    }

    @Override
    public OrderResponseDto gerOrder(Long id) {

        Optional<Order> order = orderRepository.findById(id);

        Order selectedOrder;

        if (order.isPresent()) {
            selectedOrder = order.get();
        } else {
            throw new EntityNotFoundException();
        }

        OrderResponseDto orderResponseDto = beanConfiguration.modelMapper()
                .map(selectedOrder, OrderResponseDto.class);

        return orderResponseDto;

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

/*     @Override
   public Order takeOrders(List<OrderDto> orderDtoList) {

        Member member = memberRepository.findByEmail(orderDtoList.get(0).getEmail());

        List<OrderMenu> orderMenuList = new ArrayList<>();

        for (OrderDto orderDto : orderDtoList) {

            Menu menu = jpaMenuRepository.selectMenu(orderDto.getMenuId());

            OrderMenu orderMenu = orderMenuService.createOrderMenu(menu, orderDto.getOrderQuantity());
            orderMenuList.add(orderMenu);

        }

        Order cartOrder = createOrder(member, orderMenuList);
        orderRepository.save(cartOrder);

        return cartOrder;

    }*/

}
