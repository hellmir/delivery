package personal.delivery.dto;

import lombok.Data;

@Data
public class MenuChangeDto {

    private Long id;
    private String name;
    private int price;
    private int salesRate;
    private int stock;
    private String flavor;
    private int portions;
    private int cookingTime;
    private String menuType;
    private String foodType;

}
