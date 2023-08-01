package personal.delivery.cart.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import personal.delivery.base.BaseEntity;
import personal.delivery.menu.Menu;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "cart_menu")
@Component
@NoArgsConstructor
public class CartMenu extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "cart_menu_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private Integer menuPrice;

    @Column(nullable = false)
    private int menuQuantity;

    private int totalCartMenuPrice;

    private LocalDateTime registrationTime;
    private LocalDateTime updateTime;

    private CartMenu(Menu menu, Integer menuPrice) {
        this.menu = menu;
        this.menuPrice = menuPrice;
    }

    public static CartMenu createCartMenu(Menu menuToAddCart, Integer menuPrice) {
        return new CartMenu(menuToAddCart, menuPrice);
    }

    public void addMenuQuantityAndComputeTotalPrice(Integer menuQuantity) {
        this.menuQuantity += menuQuantity;

        totalCartMenuPrice += menuPrice * menuQuantity;
    }

    public void updateCart(Cart savedCart) {
        cart = savedCart;
    }
}
