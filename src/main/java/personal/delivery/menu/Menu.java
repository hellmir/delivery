package personal.delivery.menu;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import personal.delivery.base.BaseEntity;
import personal.delivery.constant.MenuType;
import personal.delivery.constant.StockStatus;
import personal.delivery.exception.OutOfStockException;
import personal.delivery.shop.entity.Shop;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "menu")
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer salesRate;

    @Column(nullable = false)
    private Integer stock;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StockStatus stockStatus;

    @Column(length = 10)
    private String flavor;

    private Integer portions;
    private Integer cookingTime;

    @Enumerated(EnumType.STRING)
    private MenuType menuType;

    @Column(length = 10)
    private String foodType;

    private Boolean isPopularMenu;
    private List<String> menuOptions;

    private LocalDateTime registrationTime;
    private LocalDateTime updateTime;

    @Builder
    private Menu(Long id, Shop shop, String name, Integer price, Integer salesRate, Integer stock,
                String flavor, Integer portions, Integer cookingTime, MenuType menuType,
                String foodType, Boolean isPopularMenu, List<String> menuOptions,
                LocalDateTime registrationTime, LocalDateTime updateTime) {

        this.id = id;
        this.shop = shop;
        this.name = name;
        this.price = price;
        this.salesRate = salesRate;

        this.stock = stock;

        adjustStockState();

        this.flavor = flavor;
        this.portions = portions;
        this.cookingTime = cookingTime;
        this.menuType = menuType;
        this.foodType = foodType;
        this.isPopularMenu = isPopularMenu;
        this.menuOptions = menuOptions;
        this.registrationTime = registrationTime;
        this.updateTime = updateTime;


    }

    public void useStockForSale(int orderQuantity) {

        if (orderQuantity > stock) {
            throw new OutOfStockException("판매 불가: 재료 부족 (현재 재고: " + stock + ")");
        }

        stock -= orderQuantity;

        adjustStockState();

        salesRate += orderQuantity;

        if (salesRate >= 100) {
            isPopularMenu = true;
            name += "(인기메뉴)";
        } else {
            isPopularMenu = false;
            name.replace("(인기메뉴)", "");
        }

    }

    private void adjustStockState() {

        stockStatus = stock > 10 ? StockStatus.AVAILABLE
                : stock > 0 ? StockStatus.LOW_STOCK
                : StockStatus.OUT_OF_STOCK;

    }

}
