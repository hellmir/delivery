package personal.delivery.shop.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShopResponseDto {

    private Long id;
    private String name;
    private String email;
    private LocalDateTime registeredTime;
    private LocalDateTime updatedTime;

}
