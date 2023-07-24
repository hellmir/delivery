package personal.delivery.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.menu.Menu;
import personal.delivery.order.entity.Order;
import personal.delivery.order.entity.OrderMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OrderMenuService {

    public List<OrderMenu> createOrderMenu(Map<Menu, Integer> menuToOrderMap) {

        List<OrderMenu> orderMenuList = new ArrayList<>();

        for (Menu menuToOrder : menuToOrderMap.keySet()) {

            OrderMenu orderMenu = OrderMenu.createOrderMenu
                    (menuToOrder, menuToOrder.getPrice(), menuToOrderMap.get(menuToOrder));

            orderMenuList.add(orderMenu);

            menuToOrder.useStockForSale(orderMenu.getOrderQuantity());

        }

        return orderMenuList;

    }

    public void updateOrderToOrderMenuList(List<OrderMenu> orderMenuList, Order savedOrder) {

        for (OrderMenu orderMenu : orderMenuList) {

            orderMenu.updateOrder(savedOrder);

        }

    }

}
