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

    @Column(length = 20)
    private String name;

    @Column
    private Integer price;

    @Column
    private Integer salesRate;

    @Column
    private Integer stock;

    @Column(length = 10)
    private String flavor;

    private Integer portions;
    private Integer cookingTime;

    @Column(length = 5)
    private String menuType;

    @Column(length = 10)
    private String foodType;

    private Boolean popularMenu;
    private LocalDateTime registrationTime;
    private LocalDateTime updateTime;

    @Builder
    public Menu(Long id, String name, Integer price, Integer salesRate, Integer stock,
                String flavor, Integer portions, Integer cookingTime, String menuType,
                String foodType, Boolean popularMenu, LocalDateTime registrationTime, LocalDateTime updateTime) {

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
        this.registrationTime = registrationTime;
        this.updateTime = updateTime;

    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updatePrice(Integer price) {
        this.price = price;
    }

    public void updateSalesRate(Integer salesRate) {
        this.salesRate = salesRate == -1 ? 0 : salesRate;
    }

    public void updateFlavor(String flavor) {
        this.flavor = flavor;
    }

    public void updatePortions(Integer portions) {
        this.portions = portions;
    }

    public void updateCookingTime(Integer cookingTime) {
        this.cookingTime = cookingTime;
    }

    public void updateMenuType(String menuType) {
        this.menuType = menuType;
    }

    public void updateFoodType(String foodType) {
        this.foodType = foodType;
    }

    public void updateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public void addStock(int additionalStock) {

        stock += additionalStock;

    }

    public void useStockForSale(int orderQuantity) {

        int presentStock = stock - orderQuantity;

        adjustStockState(presentStock);

        stock = presentStock;
        salesRate += orderQuantity;

        name.replace("(인기메뉴)", "");

        if (salesRate >= 100) {
            popularMenu = true;
            name += "(인기메뉴)";
        }

    }

    private void adjustStockState(int presentStock) {

        if (presentStock == 0) {
            name += "(재료 소진)";
        }

        if (presentStock > 0) {
            name.replace("(재료 소진)", "");
        }

        if (presentStock < 0) {
            throw new OutOfStockException("판매 불가 : 재료 부족");
        }

    }

}
