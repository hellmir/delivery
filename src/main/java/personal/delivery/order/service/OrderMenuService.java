package personal.delivery.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.menu.Menu;
import personal.delivery.order.entity.Order;
import personal.delivery.order.entity.OrderMenu;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderMenuService {

    private final OrderMenu orderMenu;

    public OrderMenu createOrderMenu(Menu menu, int orderQuantity) {

        orderMenu.setMenu(menu);
        orderMenu.setOrderQuantity(orderQuantity);
        orderMenu.setOrderPrice(menu.getPrice());

        menu.useStockForSale(orderQuantity);

        getTotalPrice();

        return orderMenu;

    }

    public void addOrderMenu(OrderMenu orderMenu) {

        Order order = new Order();


        order.getOrderMenus().add(orderMenu);
        orderMenu.setOrder(order);
    }

    public int getTotalPrice() {

        int totalPrice = orderMenu.getOrderPrice() * orderMenu.getOrderQuantity();

        return totalPrice;

    }

}
