package personal.delivery.menu.dto;

import lombok.Data;

import java.util.List;

@Data
public class MenuDto {

    private Long shopId;
    private String name;
    private int price;
    private int salesRate;
    private int stock;
    private String flavor;
    private int portions;
    private int cookingTime;
    private String menuType;
    private String foodType;
    private List<String> menuOption;

}
