package personal.delivery.order.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import personal.delivery.validation.group.OnCreate;

import java.util.Map;

@Data
public class OrderDto {

    @NotEmpty(message = "주문 ID는 필수값입니다.")
    private Long orderId;

    @NotEmpty(groups = OnCreate.class, message = "주문 목록은 필수값입니다.")
    private Map<@NotNull(groups = OnCreate.class, message = "menuId는 필수값입니다.") Long,
            @Min(groups = OnCreate.class, value = 1, message = "orderQuantity의 최소 수량은 1개입니다.")
            @Max(groups = OnCreate.class, value = 999, message = "orderQuantity의 최대 수량은 999개입니다.") Integer>
            menuIdAndQuantityMap;

    @NotBlank(groups = OnCreate.class, message = "이메일 주소는 필수값입니다.")
    @Email(groups = OnCreate.class, message = "이메일 주소 형식이 잘못되었습니다.")
    @NotBlank(message = "이메일 주소는 필수값입니다.")
    @Email(message = "이메일 주소 형식이 잘못되었습니다.")
    private String email;

    private String orderRequest;
    private String deliveryRequest;

}
