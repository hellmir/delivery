package personal.delivery.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import personal.delivery.menu.Menu;

@Entity
@Getter
@Component
@NoArgsConstructor
public class OrderMenu {

    @Id
    @GeneratedValue
    @Column(name = "order_menu_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_id")
    private Order order;

    private Integer menuPrice;
    private Integer orderQuantity;
    private Integer totalMenuPrice;

    private OrderMenu(Menu menu, Order order, Integer menuPrice, Integer orderQuantity) {

        this.menu = menu;
        this.order = order;
        this.menuPrice = menuPrice;
        this.orderQuantity = orderQuantity;

        totalMenuPrice = menuPrice * orderQuantity;

    }

    public static OrderMenu createOrderMenu(Menu menu, Order order, Integer menuPrice, Integer orderQuantity) {
        return new OrderMenu(menu, order, menuPrice, orderQuantity);
    }

}
