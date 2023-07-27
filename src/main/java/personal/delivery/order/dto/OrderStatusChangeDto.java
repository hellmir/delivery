package personal.delivery.order.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderStatusChangeDto {

    @NotBlank(message = "이메일 주소는 필수값입니다.")
    @Email(message = "이메일 주소 형식이 잘못되었습니다.")
    private String email;

    @NotNull(message = "주문 진행 여부는 필수값입니다.")
    private Boolean isOrderInProgress;

    private Integer estimatedRequiredTime;

}
