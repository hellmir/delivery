package personal.delivery.order.dto;

import lombok.Data;
import personal.delivery.order.constant.OrderStatus;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderResponseDto {

    private Long id;
    private String shopName;
    private LocalDateTime orderTime;
    private LocalTime estimatedArrivalTime;
    private OrderStatus orderStatus;
    private List<List<String>> menuDetailsList = new ArrayList<>();
    private Integer totalOrderPrice;
    private String orderRequest;
    private String deliveryRequest;

    private LocalDateTime registeredTime;
    private LocalDateTime updatedTime;

    public void addMenuDetails(List<String> menuDetails) {
        menuDetailsList.add(menuDetails);
    }

}
