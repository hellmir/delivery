package personal.delivery.shop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShopRequestDto {

    @NotBlank(message = "가게 이름은 필수값입니다.")
    private String name;

    @NotBlank(message = "이메일 주소는 필수값입니다.")
    @Email(message = "이메일 주소 형식이 잘못되었습니다.")
    private String email;

}
