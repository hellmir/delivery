package personal.delivery.order.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderDto {

    @NotNull
    private Long menuId;

    @Min(value = 1)
    @Max(value = 999)
    private int orderQuantity;

    String email;
}
