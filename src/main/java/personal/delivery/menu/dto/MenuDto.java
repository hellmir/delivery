package personal.delivery.menu.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MenuDto {

    private String name;
    private int price;
    private int salesRate;
    private int stock;
    private String flavor;
    private int portions;
    private int cookingTime;
    private String menuType;
    private String foodType;
    private boolean popularMenu;

}
