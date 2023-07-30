package personal.delivery.cart.entity;

import jakarta.persistence.*;
import lombok.Builder;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(nullable = false)
    private Integer menuQuantity;

    private LocalDateTime registrationTime;
    private LocalDateTime updateTime;

    @Builder
    private CartMenu(Long id, Cart cart, Menu menu, Integer menuQuantity,
                     LocalDateTime registrationTime, LocalDateTime updateTime) {

        this.cart = cart;
        this.menu = menu;
        this.menuQuantity = menuQuantity;
        this.registrationTime = registrationTime;
        this.updateTime = updateTime;

    }

    public void updateMenuQuantity(Integer menuQuantity) {
        this.menuQuantity = menuQuantity;


    }

}
