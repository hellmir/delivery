package personal.delivery.member.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import personal.delivery.member.entity.Address;

@Data
public class MemberRequestDto {

    @NotBlank(message = "이름은 필수값입니다.")
    private String name;

    @NotBlank(message = "이메일 주소는 필수값입니다.")
    @Email(message = "이메일 주소 형식이 잘못되었습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수값입니다.")
    private String password;

    @Valid
    @NotNull(message = "주소는 필수값입니다.")
    private Address address;

}
