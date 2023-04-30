package personal.delivery.menu;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import personal.delivery.exception.OutOfStockException;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer salesRate;

    @Column(nullable = false)
    private Integer stock;

    private String flavor;
    private Integer portions;
    private Integer cookingTime;
    private String menuType;
    private String foodType;
    private Boolean popularMenu;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;

    @Builder
    public Menu(Long id, String name, Integer price, Integer salesRate, Integer stock, String flavor, Integer portions,
                Integer cookingTime, String menuType, String foodType, Boolean popularMenu) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.salesRate = salesRate;
        this.stock = stock;
        this.flavor = flavor;
        this.portions = portions;
        this.cookingTime = cookingTime;
        this.menuType = menuType;
        this.foodType = foodType;
        this.popularMenu = popularMenu;
    }

    public void updateMenu(String name, Integer price, Integer salesRate, String flavor, Integer portions,
                    Integer cookingTime, String menuType,String foodType, Boolean popularMenu) {

        this.name = name;
        this.price = price;
        this.salesRate = salesRate;
        this.flavor = flavor;
        this.portions = portions;
        this.cookingTime = cookingTime;
        this.menuType = menuType;
        this.foodType = foodType;
        this.popularMenu = popularMenu;
        
    }

    public void importPresentStock(int presentStock) {
        this.stock = presentStock;
    }

    public void addStock(int stock) {
        this.stock = this.stock + stock;
    }

    public void useStockForSale(int stock) {

        int presentStock = this.stock - stock;

        adjustStockState(presentStock);

        this.stock = presentStock;
        salesRate = salesRate + stock;

    }

    private void adjustStockState(int presentStock) {

        if (presentStock == 0) {
            this.name += "(재료 소진)";
        }

        if (presentStock > 0) {
            this.name.replace("(재료 소진)", "");
        }

        if (presentStock < 0) {
            throw new OutOfStockException("판매 불가 : 재료 부족");
        }

    }

}
