package personal.delivery.member.dto;

import lombok.Data;
import personal.delivery.constant.Role;

@Data
public class MemberResponseDto {

    private String name;
    private String email;
    private String address;
    private Role role;

}
