package personal.delivery.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.stereotype.Component;
import personal.delivery.menu.Menu;

@Entity
@Getter
@Component
public class OrderMenu {

    @Id
    @GeneratedValue
    @Column(name = "order_menu_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private Integer orderPrice;
    private Integer orderQuantity;
    private Integer totalPrice;

    public void createOrderMenu(Menu menu, Integer orderPrice, Integer orderQuantity) {

        this.menu = menu;
        this.orderPrice = orderPrice;
        this.orderQuantity = orderQuantity;

    }

    public void updateOrder(Order order) {

        this.order = order;

    }
}
