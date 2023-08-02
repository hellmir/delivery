package personal.delivery.order.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import personal.delivery.validation.group.OnCreate;

import java.util.Map;

@Data
public class OrderRequestDto {

    @NotNull(groups = OnCreate.class, message = "회원 ID는 필수값입니다.")
    @NotNull(message = "회원 ID는 필수값입니다.")
    private Long memberId;

    @NotNull(message = "주문 ID는 필수값입니다.")
    private Long orderId;

    @NotEmpty(groups = OnCreate.class, message = "주문 목록은 필수값입니다.")
    private Map<@NotNull(groups = OnCreate.class, message = "menuId는 필수값입니다.") Long,
            @Min(groups = OnCreate.class, value = 1, message = "orderQuantity의 최소 수량은 1개입니다.")
            @Max(groups = OnCreate.class, value = 999, message = "orderQuantity의 최대 수량은 999개입니다.") Integer>
            menuIdAndQuantityMap;

    private String orderRequest;
    private String deliveryRequest;

}
