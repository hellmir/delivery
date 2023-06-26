package personal.delivery.order.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class OrderDto {

    private Long orderId;

    private Long menuId;

    @Min(value = 1)
    @Max(value = 999)
    private int orderQuantity;

    private String orderRequest;
    private String deliveryRequest;
    String email;

}
