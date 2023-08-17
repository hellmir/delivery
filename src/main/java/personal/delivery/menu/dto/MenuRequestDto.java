package personal.delivery.menu.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import personal.delivery.menu.constant.MenuType;
import personal.delivery.validation.group.OnCreate;
import personal.delivery.validation.group.OnUpdate;

import java.util.List;

@Data
public class MenuRequestDto {

    private Long shopId;

    @NotBlank(groups = OnCreate.class, message = "메뉴 이름은 필수값입니다.")
    private String name;

    @Positive(groups = OnCreate.class, message = "가격은 양수값이 입력되어야 합니다.")
    @Min(groups = OnUpdate.class, value = 0, message = "가격은 음수값이 될 수 없습니다.")
    private int price;

    @Max(groups = OnCreate.class, value = 0, message = "신메뉴의 판매량은 설정할 수 없습니다.")
    @Min(groups = OnCreate.class, value = 0, message = "신메뉴의 판매량은 설정할 수 없습니다.")
    @Max(groups = OnUpdate.class, value = 0, message = "판매량은 수정할 수 없습니다. 판매량을 초기화하려면 -1을 입력합니다.")
    @Min(groups = OnUpdate.class, value = -1, message = "판매량은 수정할 수 없습니다. 판매량을 초기화하려면 -1을 입력합니다.")
    private int salesRate;

    @Min(groups = OnCreate.class, value = 0, message = "재고는 음수값이 될 수 없습니다.")
    private int stock;

    private String flavor;

    @Min(groups = OnCreate.class, value = 0, message = "인분은 음수값이 될 수 없습니다.")
    @Min(groups = OnUpdate.class, value = 0, message = "인분은 음수값이 될 수 없습니다.")
    private int portions;

    @Min(groups = OnCreate.class, value = 0, message = "조리시간은 음수값이 될 수 없습니다.")
    @Min(groups = OnUpdate.class, value = 0, message = "조리시간은 음수값이 될 수 없습니다.")
    private int cookingTime;

    private MenuType menuType;
    private String foodType;
    private List<String> menuOptions;

}
