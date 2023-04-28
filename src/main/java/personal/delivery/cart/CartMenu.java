package personal.delivery.cart;

import jakarta.persistence.*;
import lombok.Getter;
import personal.delivery.menu.Menu;

@Entity
@Getter
@Table(name = "cart_menu")
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

    private int numOfMenu;

}
