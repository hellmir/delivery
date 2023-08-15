package personal.delivery.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import personal.delivery.base.BaseEntity;
import personal.delivery.menu.entity.Menu;

@Entity
@Getter
@NoArgsConstructor
public class OrderMenu extends BaseEntity {

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

    @Column(nullable = false)
    private Integer menuPrice;

    @Column(nullable = false)
    private Integer orderQuantity;

    @Column(nullable = false)
    private Integer totalMenuPrice;

    private OrderMenu(Menu menu, Integer menuPrice, Integer orderQuantity) {

        this.menu = menu;
        this.menuPrice = menuPrice;
        this.orderQuantity = orderQuantity;

        totalMenuPrice = menuPrice * orderQuantity;

    }

    public static OrderMenu createOrderMenu(Menu menu, Integer menuPrice, Integer orderQuantity) {
        return new OrderMenu(menu, menuPrice, orderQuantity);
    }

    public void updateOrder(Order savedOrder) {
        order = savedOrder;
    }

}
