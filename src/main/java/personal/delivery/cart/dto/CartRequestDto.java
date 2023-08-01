package personal.delivery.cart.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import personal.delivery.validation.group.OnCreate;

import java.util.Map;

@Data
public class CartRequestDto {

    private Long memberId;

    @NotNull(groups = OnCreate.class, message = "가게 ID는 필수값입니다.")
    private Long shopId;

    @NotNull(message = "장바구니 ID는 필수값입니다.")
    private Long cartId;

    @NotNull(message = "장바구니 메뉴 ID는 필수값입니다.")
    private Long cartMenuId;

    @NotEmpty(groups = OnCreate.class, message = "장바구니 요청 목록은 필수값입니다.")
    private Map<@NotNull(groups = OnCreate.class, message = "menuId는 필수값입니다.") Long,
            @Min(groups = OnCreate.class, value = 1, message = "장바구니 메뉴 최소 수량은 1개입니다.")
            @Max(groups = OnCreate.class, value = 999, message = "장바구니 메뉴 최대 수량은 999개입니다.") Integer>
            menuIdAndQuantityMap;

}
