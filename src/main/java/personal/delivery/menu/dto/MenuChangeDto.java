package personal.delivery.menu.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MenuChangeDto {

    @NotNull
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
