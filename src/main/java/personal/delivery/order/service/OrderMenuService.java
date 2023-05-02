package personal.delivery.order.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.menu.Menu;
import personal.delivery.order.entity.Order;
import personal.delivery.order.entity.OrderMenu;

@Service
@Transactional
@AllArgsConstructor
public class OrderMenuService {

    private final OrderMenu orderMenu;
    private final Order order;

    public OrderMenu createOrderMenu(Menu menu, int orderQuantity) {

        orderMenu.setMenu(menu);
        orderMenu.setOrderPrice(menu.getPrice());
        orderMenu.setOrderQuantity(orderQuantity);
        orderMenu.setTotalPrice(getTotalPrice());

        menu.useStockForSale(orderQuantity);

        return orderMenu;

    }

    public int getTotalPrice() {

        int totalPrice = orderMenu.getOrderPrice() * orderMenu.getOrderQuantity();

        return totalPrice;

    }

}
