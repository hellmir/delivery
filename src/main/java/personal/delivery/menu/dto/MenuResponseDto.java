package personal.delivery.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import personal.delivery.menu.constant.MenuType;
import personal.delivery.menu.constant.StockStatus;
import personal.delivery.shop.dto.ShopResponseDto;
import personal.delivery.shop.entity.Shop;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MenuResponseDto {

    private Long id;
    private Long shopId;
    private String shopName;
    private String name;
    private int price;
    private int salesRate;
    private int stock;
    private StockStatus stockStatus;
    private String flavor;
    private int portions;
    private int cookingTime;
    private MenuType menuType;
    private String foodType;
    private boolean isPopularMenu;
    private List<String> menuOptions;

    private LocalDateTime registeredTime;
    private LocalDateTime updatedTime;

}
