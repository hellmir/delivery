package personal.delivery.order.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.menu.Menu;
import personal.delivery.order.entity.OrderMenu;

@Service
@Transactional
@AllArgsConstructor
public class OrderMenuService {

    private final OrderMenu orderMenu;

    public OrderMenu createOrderMenu(Menu menu, int orderQuantity) {

        orderMenu.createOrderMenu(menu, menu.getPrice(), orderQuantity);

        menu.useStockForSale(orderQuantity);

        return orderMenu;

    }

    public int getMenuTotalPrice() {

        int totalPrice = orderMenu.getOrderPrice() * orderMenu.getOrderQuantity();

        return totalPrice;

    }

}
