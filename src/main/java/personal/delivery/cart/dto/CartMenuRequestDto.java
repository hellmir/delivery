package personal.delivery.cart.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import personal.delivery.validation.group.OnCreate;

// TODO: DTO 이름 변경
@Data
public class CartMenuRequestDto {

    @NotNull(message = "장바구니 ID는 필수값입니다.")
    private Long cartId;

    @NotNull(message = "장바구니 메뉴 ID는 필수값입니다.")
    private Long cartMenuId;

    @NotNull(groups = OnCreate.class, message = "메뉴 ID는 필수값입니다.")
    private Long menuId;

    @Min(groups = OnCreate.class, value = 1, message = "장바구니 메뉴 최소 수량은 1개입니다.")
    @Max(groups = OnCreate.class, value = 999, message = "장바구니 메뉴 최대 수량은 999개입니다.")
    private int menuQuantity;

    @NotBlank(groups = OnCreate.class, message = "이메일 주소는 필수값입니다.")
    @NotBlank(message = "이메일 주소는 필수값입니다.")
    @Email(groups = OnCreate.class, message = "이메일 주소 형식이 잘못되었습니다.")
    @Email(message = "이메일 주소 형식이 잘못되었습니다.")
    private String email;

}
