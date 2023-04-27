package personal.delivery.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MenuResponseDto {

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
    private boolean popularMenu;

}
