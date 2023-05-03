package personal.delivery.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.config.BeanConfiguration;
import personal.delivery.constant.OrderStatus;
import personal.delivery.member.Member;
import personal.delivery.member.repository.MemberRepository;
import personal.delivery.menu.Menu;
import personal.delivery.menu.repository.JpaMenuRepository;
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
public class OrderServiceImpl implements OrderService {

    private final JpaMenuRepository jpaMenuRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final BeanConfiguration beanConfiguration;
    private final OrderMenuService orderMenuService;

    @Autowired
    public OrderServiceImpl(JpaMenuRepository jpaMenuRepository, MemberRepository memberRepository,
                            OrderRepository orderRepository, BeanConfiguration beanConfiguration,
                            OrderMenuService orderMenuService) {

        this.jpaMenuRepository = jpaMenuRepository;
        this.memberRepository = memberRepository;
        this.orderRepository = orderRepository;
        this.beanConfiguration = beanConfiguration;
        this.orderMenuService = orderMenuService;

    }

    @Override
    public OrderResponseDto takeOrder(OrderDto orderDto) {

        Menu menu = jpaMenuRepository.selectMenu(orderDto.getMenuId());
        Member member = memberRepository.findByEmail(orderDto.getEmail());

        List<OrderMenu> orderMenuList = new ArrayList<>();
        OrderMenu orderMenu = orderMenuService.createOrderMenu(menu, orderDto.getOrderQuantity());
        orderMenuList.add(orderMenu);

        Order order = createOrder(member, orderMenuList);

        Order savedOrder = orderRepository.save(order);

        OrderResponseDto orderResponseDto = beanConfiguration.modelMapper()
                .map(savedOrder, OrderResponseDto.class);

        return orderResponseDto;

    }

    @Override
    public Order createOrder(Member member, List<OrderMenu> orderMenuList) {

        Order order = Order.builder()
                .orderTime(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDER)
                .member(member)
                .registrationTime(LocalDateTime.now())
                .build();

        List<OrderMenu> orderMenus = order.getOrderMenus();

        order.computeTotalPrice(getMenuListTotalPrice(orderMenus));

        for (OrderMenu orderMenu : orderMenuList) {
            order.addOrderMenu(orderMenu);
        }

        return order;

    }

    @Override
    public int getMenuListTotalPrice(List<OrderMenu> orderMenus) {

        int totalPrice = 0;

        for (OrderMenu orderMenu : orderMenus) {

            totalPrice += orderMenu.getMenuTotalPrice();

        }

        return totalPrice;

    }

}
