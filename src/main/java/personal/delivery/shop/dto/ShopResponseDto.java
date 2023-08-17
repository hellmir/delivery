package personal.delivery.shop.dto;

import lombok.Data;
import personal.delivery.menu.dto.MenuResponseDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ShopResponseDto {

    private Long id;
    private String name;
    private Long memberId;
    private String memberName;
    private List<MenuResponseDto> menuResponseDtoList;
    private LocalDateTime registeredTime;
    private LocalDateTime updatedTime;

}
