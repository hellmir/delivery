package personal.delivery.order.dto;

import lombok.Data;
import personal.delivery.constant.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderResponseDto {

    private Long id;
    private LocalDateTime orderTime;
    private OrderStatus orderStatus;
    private List<List<String>> menuDetailsList = new ArrayList<>();
    private Integer totalOrderPrice;
    private String orderRequest;
    private String deliveryRequest;

    private LocalDateTime registrationTime;
    private LocalDateTime updateTime;

    public void addMenuDetails(List<String> menuDetails) {
        menuDetailsList.add(menuDetails);
    }

}
