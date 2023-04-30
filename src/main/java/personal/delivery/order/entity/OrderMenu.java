package personal.delivery.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import personal.delivery.menu.Menu;

@Entity
@Getter
@Setter
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

}
