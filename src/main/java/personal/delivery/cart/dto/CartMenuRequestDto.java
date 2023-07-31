package personal.delivery.cart.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import personal.delivery.validation.group.OnCreate;

@Data
public class CartMenuRequestDto {

    private Long memberId;

    @NotNull(message = "장바구니 ID는 필수값입니다.")
    private Long cartId;

    @NotNull(message = "장바구니 메뉴 ID는 필수값입니다.")
    private Long cartMenuId;

    @NotNull(groups = OnCreate.class, message = "메뉴 ID는 필수값입니다.")
    private Long menuId;

    @Min(groups = OnCreate.class, value = 1, message = "장바구니 메뉴 최소 수량은 1개입니다.")
    @Max(groups = OnCreate.class, value = 999, message = "장바구니 메뉴 최대 수량은 999개입니다.")
    private int menuQuantity;

}
