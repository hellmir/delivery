package personal.delivery.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import personal.delivery.constant.StockStatus;
import personal.delivery.shop.entity.Shop;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MenuResponseDto {

    private Long id;
    private Shop shop;
    private String name;
    private int price;
    private int salesRate;
    private int stock;
    private StockStatus stockStatus;
    private String flavor;
    private int portions;
    private int cookingTime;
    private String menuType;
    private String foodType;
    private boolean isPopularMenu;
    private List<String> menuOption;

    private LocalDateTime registrationTime;
    private LocalDateTime updateTime;

}
