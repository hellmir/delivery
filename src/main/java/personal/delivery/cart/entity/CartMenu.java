package personal.delivery.cart.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import personal.delivery.menu.Menu;

@Entity
@Getter
@Setter
@Table(name = "cart_menu")
@Component
public class CartMenu {

    @Id
    @GeneratedValue
    @Column(name = "cart_menu_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private Integer menuQuantity;

}
