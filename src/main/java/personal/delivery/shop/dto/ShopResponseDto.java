package personal.delivery.shop.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShopResponseDto {

    Long id;
    String name;
    String email;
    LocalDateTime registrationTime;

}
