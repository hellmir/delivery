package personal.delivery.cart.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CartResponseDto {

    private Long cartId;
    private String shopName;
    private List<List<String>> menuDetailsList = new ArrayList<>();
    private Integer totalCartPrice;
    private LocalDateTime registeredTime;
    private LocalDateTime updatedTime;

    public void addMenuDetails(List<String> menuDetails) {
        menuDetailsList.add(menuDetails);
    }
}
