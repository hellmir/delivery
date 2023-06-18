package personal.delivery.cart.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartMenuDto {

    @NotNull
    private Long id;

    @NotNull
    private Long menuId;

    @Min(value = 1)
    @Max(value = 999)
    private int menuQuantity;

    @NotNull
    private String email;
}
