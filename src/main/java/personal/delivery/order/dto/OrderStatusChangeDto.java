package personal.delivery.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderStatusChangeDto {

    @NotNull(message = "회원 ID는 필수값입니다.")
    private Long memberId;

    @NotNull(message = "주문 진행 여부는 필수값입니다.")
    private Boolean isOrderInProgress;

    private Integer estimatedRequiredTime;

}
