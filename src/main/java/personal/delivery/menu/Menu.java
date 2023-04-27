package personal.delivery.menu;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public void changeName(String name) {
        this.name = name;
    }

    public void changePrice(Integer price) {
        this.price = price;
    }

    public void addSalesRate(Integer salesRate) {
        this.salesRate = salesRate;
    }

    public void addStock(Integer stock) {
        this.stock = stock;
    }

    public void changeFlavor(String flavor) {
        this.flavor = flavor;
    }

    public void changePortions(Integer portions) {
        this.portions = portions;
    }

    public void changeCookingTime(Integer cookingTime) {
        this.cookingTime = cookingTime;
    }

    public void changeMenuType(String menuType) {
        this.menuType = menuType;
    }

    public void changeFoodType(String foodType) {
        this.foodType = foodType;
    }

    public void changePopularMenu(Boolean popularMenu) {
        this.popularMenu = popularMenu;
    }
}
